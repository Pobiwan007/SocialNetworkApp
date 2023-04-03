package com.social2023Network.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social2023Network.data.repository.FirebaseAuthRepositoryImpl
import javax.inject.Inject


class AuthViewModelFactory @Inject constructor(
    private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(firebaseAuthRepositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}