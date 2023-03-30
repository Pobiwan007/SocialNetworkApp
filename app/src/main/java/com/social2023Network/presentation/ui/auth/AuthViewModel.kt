package com.social2023Network.presentation.ui.auth

import androidx.lifecycle.ViewModel
import com.social2023Network.data.repository.FirebaseAuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl): ViewModel() {

}