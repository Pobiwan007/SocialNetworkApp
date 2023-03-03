package com.social2023Network.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.common.network.ApiState
import com.social2023Network.data.repository.HomeRepository
import com.social2023Network.data.remote.anime.AnimeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(private var homeRepository: HomeRepository): ViewModel(){
    var apiState : MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    var mutableDataAnime: MutableStateFlow<AnimeResponse> = MutableStateFlow(AnimeResponse())

    fun getData(filter: String? = null, category: String? = null) = viewModelScope.launch {
        apiState.value = ApiState.Loading
        val animeFlow = when {
            category != null -> homeRepository.getAnimeDataWithCategories(category)
            filter != null -> homeRepository.getAnimeDataWithSearch(filter)
            else -> homeRepository.getAnimeData()
        }
        animeFlow
            .catch { exception ->
                apiState.value = ApiState.Failure(exception)
            }
            .flowOn(Dispatchers.IO)
            .collectLatest { animeResponse ->
                apiState.value = ApiState.Success(animeResponse)
                mutableDataAnime.value = animeResponse
                Log.e("ListFromResp", animeResponse.toString())
            }
    }
}