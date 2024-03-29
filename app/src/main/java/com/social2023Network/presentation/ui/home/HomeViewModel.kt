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
import com.social2023Network.data.repository.HomeRepositoryImpl
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.model.story.Story
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.presentation.MainActivity
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
    private var homeRepository: HomeRepositoryImpl,
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

    private val _location = MutableLiveData<String>()

    private var _postsMutableData : MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    val posts = _postsMutableData.asStateFlow()

    private var _storiesMutableData : MutableStateFlow<List<Story>> = MutableStateFlow(listOf())
    val stories = _storiesMutableData.asStateFlow()
    private var _mutableMapUrlImages: MutableStateFlow<MutableMap<String, String>> = MutableStateFlow(mutableMapOf())
    val imagesUrlMap = _mutableMapUrlImages.asStateFlow()

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

    suspend fun convertDateTime(dateTime: String): String = withContext(Dispatchers.Default){
        homeUseCase.convertDate(dateTime)
    }


    fun openUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
            .setShowTitle(true)
        try {
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                MainActivity.instance,
                Uri.parse("https://www.youtube.com/watch?v=$url")
            )
        }catch (e: Exception){
            e.printStackTrace()
        }

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
             homeRepository.createPost(post, listUri)
        }
    }

    suspend fun getUrlImageByFileName(fileName: String): String = withContext(Dispatchers.IO) {
        val url = homeRepository.getImageUrlByFileName(fileName)
        if(!_mutableMapUrlImages.value.containsValue(fileName) && url.isNotEmpty()){
            _mutableMapUrlImages.value[fileName] = url
        }
        url
    }


}