package com.social2023Network.presentation.ui.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.common.AllApi
import com.social2023Network.common.network.ApiState
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.profile.Profile
import com.social2023Network.domain.model.story.Story
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.repository.HomeRepository
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

    val tempList = listOf(
        Story(
            0,
            "17.02.2023:9:00",
            "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
            profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
        ),
        Story(
            0,
            "17.02.2023:9:00",
            "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
            profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
        ),
        Story(
            0,
            "17.02.2023:9:00",
            "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
            profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
        ),
    )

    init {
        getDataAnime()
        getCurrentWeather()
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
    }

}