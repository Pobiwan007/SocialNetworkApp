package com.social2023Network.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.common.network.ApiState
import com.social2023Network.domain.repository.HomeRepository
import com.social2023Network.domain.model.anime.AnimeResponse
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
    private val converterDataUseCase: ConverterDataUseCase

) : ViewModel() {

    private var _apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    private var _mutableDataAnime: MutableStateFlow<AnimeResponse> =
        MutableStateFlow(AnimeResponse())


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
                Log.e("ListFromResp", animeResponse.toString())
            }
    }

    suspend fun onDateReceived(dateStr: String): String = withContext(Dispatchers.IO) {
        converterDataUseCase.execute(dateStr)
    }
}