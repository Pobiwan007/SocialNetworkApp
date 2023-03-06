package com.social2023Network.presentation.ui.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.common.AllApi
import com.social2023Network.common.network.ApiState
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private var homeRepository: HomeRepository,
    //private val converterDataUseCase: ConverterDataUseCase,
) : ViewModel() {

    private var _apiStateAnime: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    private var _mutableDataAnime: MutableStateFlow<AnimeResponse> =
        MutableStateFlow(AnimeResponse())

    val apiState = _apiStateAnime.asStateFlow()
    val mutableDataAnime = _mutableDataAnime.asStateFlow()

    private var _mutableDataWeather: MutableStateFlow<WeatherResponse> =
        MutableStateFlow(WeatherResponse())
    private var _apiStateWeather: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val apiStateWeather = _apiStateWeather.asStateFlow()
    val mutableDataWeather = _mutableDataWeather.asStateFlow()

    private var _context: MutableLiveData<Context> = MutableLiveData()
    private val _hasLocationPermission = MutableLiveData<Boolean>()


    init {
        getDataAnime()
    }

    fun getCurrentWeather(latitude: Double?, longitude: Double?) = viewModelScope.launch {
        _apiStateWeather.value = ApiState.Loading
        val weatherFlow =
            if (latitude == null && longitude == null) homeRepository.getCurrentWeather(AllApi.DEFAULT_CITY)
            else homeRepository.getCurrentWeather("${latitude},${longitude}")
        weatherFlow
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

//    suspend fun onDateReceived(dateStr: String): String = withContext(Dispatchers.IO) {
//        converterDataUseCase.execute(dateStr)
//    }

    fun openUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
            .setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(
            _context.value!!,
            Uri.parse("https://www.youtube.com/watch?v=$url")
        )
    }

    fun setLocationPermission(isGranted: Boolean) {
        _hasLocationPermission.value = isGranted
    }

}