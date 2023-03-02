package com.social2023Network.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.network.ApiState
import com.social2023Network.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private var networkRepository: HomeRepository) : ViewModel(){
    var dataAnime : MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    fun getData(filter: String) = viewModelScope.launch {
        dataAnime.value = ApiState.Loading
        networkRepository.getAnimeData(filter)
            .catch {
                dataAnime.value = ApiState.Failure(it)
            }
            .collect{
                dataAnime.value = ApiState.Success(it)
            }

    }

    fun getDataWithCategories(category: String) = viewModelScope.launch {
        dataAnime.value = ApiState.Loading
        networkRepository.getAnimeDataWithCategories(category)
            .catch {
                dataAnime.value = ApiState.Failure(it)
            }
            .collect{
                dataAnime.value = ApiState.Success(it)
            }

    }
}