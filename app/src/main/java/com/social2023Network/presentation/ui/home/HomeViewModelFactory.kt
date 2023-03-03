package com.social2023Network.presentation.ui.home;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social2023Network.data.repository.HomeRepository
import javax.inject.Inject

class HomeViewModelFactory
        @Inject constructor(private val repository: HomeRepository
        ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
        }
}