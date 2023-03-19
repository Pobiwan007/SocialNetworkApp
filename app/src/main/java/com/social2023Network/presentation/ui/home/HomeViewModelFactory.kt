package com.social2023Network.presentation.ui.home;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social2023Network.data.repository.HomeRepository
import com.social2023Network.domain.usecase.HomeUseCase
import javax.inject.Inject

class HomeViewModelFactory
        @Inject constructor(
            private val repository: HomeRepository,
            private val homeUseCase: HomeUseCase,
        ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(repository, homeUseCase) as T
        }
}