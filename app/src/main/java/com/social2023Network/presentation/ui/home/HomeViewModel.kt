package com.social2023Network.presentation.ui.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.util.AllApi
import com.social2023Network.util.ApiState
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.story.Story
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.data.repository.HomeRepository
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.usecase.HomeUseCase
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

    private var _mutableDataWeather: MutableStateFlow<WeatherResponse> =
        MutableStateFlow(WeatherResponse())
    private var _apiStateWeather: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val apiStateCurrentWeather = _apiStateWeather.asStateFlow()
    val mutableDataCurrentWeather = _mutableDataWeather.asStateFlow()

    private var _context: MutableLiveData<Context> = MutableLiveData()
    private val _location = MutableLiveData(AllApi.DEFAULT_CITY)

    val tempList = listOf<Story>()

    init {
        getDataAnime()
        getCurrentWeather()
        viewModelScope.launch {
            getPostsFromFirebase("path")
        }
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

    private suspend fun getPostsFromFirebase(path: String): List<Post> = withContext(Dispatchers.IO){
        homeRepository.getPostFromFirebase(path)
    }

    fun setContext(context: Context){
        _context.value = context
    }
    fun setLocation(location: String) {
        _location.value = location
    }

}