package com.social2023Network.presentation.ui.home

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.data.permissions.PermissionsManager
import com.social2023Network.data.repository.HomeRepository
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.model.story.Story
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private var homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase,
) : ViewModel() {

    private var _apiStateAnime: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    private var _mutableDataAnime: MutableStateFlow<AnimeResponse> =
        MutableStateFlow(AnimeResponse())

    val apiState = _apiStateAnime.asStateFlow()
    val mutableDataAnime = _mutableDataAnime.asStateFlow()
    lateinit var permissionsManager : PermissionsManager

    private var _mutableDataWeather: MutableStateFlow<WeatherResponse> =
        MutableStateFlow(WeatherResponse())
    private var _apiStateWeather: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val apiStateCurrentWeather = _apiStateWeather.asStateFlow()
    val mutableDataCurrentWeather = _mutableDataWeather.asStateFlow()

    private var _context: MutableLiveData<Context> = MutableLiveData()
    private val _location = MutableLiveData<String>()

    private var _postsMutableData : MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    val posts = _postsMutableData.asStateFlow()

    private var _storiesMutableData : MutableStateFlow<List<Story>> = MutableStateFlow(listOf())
    val stories = _storiesMutableData.asStateFlow()

    init {
        getDataAnime()
        getPostListFromFirebase()
    }

    private fun getCurrentWeather() = viewModelScope.launch {
        _apiStateWeather.value = ApiState.Loading
        homeRepository.getCurrentWeather(_location.value.toString())
            .catch {
                _apiStateWeather.value = ApiState.Failure(it)
            }
            .flowOn(Dispatchers.IO)
            .collectLatest {
                _apiStateWeather.value = ApiState.Success(it)
                _mutableDataWeather.value = it
            }
    }

    fun getDataAnime(filter: String? = null, category: String? = null) = viewModelScope.launch {
        _apiStateAnime.value = ApiState.Loading

        val animeFlow = when {
            category != null -> homeRepository.getAnimeDataWithCategories(category)
            filter != null && filter != "" -> homeRepository.getAnimeDataWithSearch(filter)
            else -> homeRepository.getAnimeData()
        }

        animeFlow
            .catch { exception ->
                _apiStateAnime.value = ApiState.Failure(exception)
            }
            .flowOn(Dispatchers.IO)
            .collectLatest { animeResponse ->
                _apiStateAnime.value = ApiState.Success(animeResponse)
                _mutableDataAnime.value = animeResponse
            }
    }

    suspend fun getColorByRatingName(rating: String): Color = withContext(Dispatchers.Default) {
        homeUseCase.getColorByRating(rating)
    }


    fun openUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
            .setShowTitle(true)
        try {
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                _context.value!!,
                Uri.parse("https://www.youtube.com/watch?v=$url")
            )
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    fun setContext(context: Context){
        _context.value = context
    }
    fun setLocation(location: String) {
        _location.value = location
        getCurrentWeather()
    }

    private fun getPostListFromFirebase() = viewModelScope.launch {
        homeRepository.getPosts().collect{
            when{
                it.isSuccess -> {
                    _postsMutableData.value = it.getOrThrow()
                }
                it.isFailure -> {
                    Log.e("POSTS:ViewModel", "FAIL")
                }
            }
        }
    }

    suspend fun createNewPostInFirebase(post: Post, listUri: SnapshotStateList<Uri?>){
        viewModelScope.launch {
             homeRepository.createPost(post, _context.value!!, listUri, callProgressAlertDialog = {
                 permissionsManager.dialogManager.showProgressDialog(
                     it,
                     _context.value!!
                 )
             })
        }
    }
}