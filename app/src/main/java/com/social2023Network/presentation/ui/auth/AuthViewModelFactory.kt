package com.social2023Network.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social2023Network.data.repository.FirebaseAuthRepositoryImpl
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(firebaseAuthRepositoryImpl) as T
    }
}