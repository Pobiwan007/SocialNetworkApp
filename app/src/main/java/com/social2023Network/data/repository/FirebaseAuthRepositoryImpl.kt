package com.social2023Network.data.repository

import com.google.firebase.auth.PhoneAuthProvider
import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.domain.base.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseManager: FirebaseManager
    ): FirebaseAuthRepository {

    override suspend fun loginWithPhoneNumber(
        phoneNumber: String?,
        verificationCode: String?,
        callback: FirebaseAuthRepository.AuthCallback?
    ) {
        if (phoneNumber == null) {
            callback?.onLoginFailure(NullPointerException("Phone number is null"))
            return
        }

        if (verificationCode == null) {
            callback?.onLoginFailure(NullPointerException("Verification code is null"))
            return
        }

        val credential = PhoneAuthProvider.getCredential(phoneNumber, verificationCode)

        try {
            withContext(Dispatchers.IO) {
                firebaseManager.getFirebaseAuthentication().signInWithCredential(credential).await()
            }?.user?.let { user ->
                callback?.onLoginSuccess(user)
            } ?: callback?.onLoginFailure(NullPointerException("User is null"))
        } catch (e: Exception) {
            callback?.onLoginFailure(e)
        }
    }
}