package com.social2023Network.presentation.ui.home;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social2023Network.domain.repository.HomeRepository
import com.social2023Network.domain.usecase.ConverterDataUseCase
import javax.inject.Inject

class HomeViewModelFactory
        @Inject constructor(
                private val repository: HomeRepository,
        //       private val converterDataUseCase: ConverterDataUseCase,
        ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
        }
}