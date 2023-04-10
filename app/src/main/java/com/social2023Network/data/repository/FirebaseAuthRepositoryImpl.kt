package com.social2023Network.data.repository

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.data.network.RetrofitClient
import com.social2023Network.domain.base.FirebaseAuthRepository
import com.social2023Network.domain.model.countries.CountriesResponse
import com.social2023Network.presentation.MainActivity
import com.social2023Network.util.CoroutineProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseManager: FirebaseManager,
    //private val resourceProvider: ResourceProvider
): FirebaseAuthRepository, CoroutineProvider {

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

    override suspend fun sendVerificationCode(phoneNumber: String): Boolean = withContext(Dispatchers.IO){
        return@withContext try {
            // Call Firebase Auth to send verification code
            val options = PhoneAuthOptions.newBuilder(firebaseManager.getFirebaseAuthentication())
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(MainActivity.instance) // Pass your activity reference here
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    // Handle verification state changes
                    // ...
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Log.e("FirebaseAuth", p0.smsCode.toString())
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Log.e("FirebaseAuth", "failed${p0.printStackTrace()}")
                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            true
        } catch (e: Exception) {
            // Handle error
            e.printStackTrace()
            false
        }
    }

    suspend fun getCountries(): Flow<List<CountriesResponse>> = flowOnIO {
        RetrofitClient.retrofitCountries.getAllCountries().await()
    }
}