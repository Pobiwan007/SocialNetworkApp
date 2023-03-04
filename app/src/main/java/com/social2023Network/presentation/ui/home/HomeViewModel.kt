package com.social2023Network.presentation.ui.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.common.network.ApiState
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.repository.HomeRepository
import com.social2023Network.domain.usecase.ConverterDataUseCase
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
    private val converterDataUseCase: ConverterDataUseCase,
) : ViewModel() {

    private var _apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    private var _mutableDataAnime: MutableStateFlow<AnimeResponse> =
        MutableStateFlow(AnimeResponse())

    private var _context : MutableLiveData<Context> = MutableLiveData()

    val apiState = _apiState.asStateFlow()
    val mutableDataAnime = _mutableDataAnime.asStateFlow()

    fun getDataAnime(filter: String? = null, category: String? = null) = viewModelScope.launch {
        _apiState.value = ApiState.Loading

        val animeFlow = when {
            category != null -> homeRepository.getAnimeDataWithCategories(category)
            filter != null -> homeRepository.getAnimeDataWithSearch(filter)
            else -> homeRepository.getAnimeData()
        }

        animeFlow
            .catch { exception ->
                _apiState.value = ApiState.Failure(exception)
            }
            .flowOn(Dispatchers.IO)
            .collectLatest { animeResponse ->
                _apiState.value = ApiState.Success(animeResponse)
                _mutableDataAnime.value = animeResponse
            }
    }

    suspend fun onDateReceived(dateStr: String): String = withContext(Dispatchers.IO) {
        converterDataUseCase.execute(dateStr)
    }

    fun openUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
            .setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(_context.value!!, Uri.parse("https://www.youtube.com/watch?v=$url"))
    }

    fun setContext(context: Context){
        _context.value = context
    }
}