package com.social2023Network.domain.base

import com.google.firebase.auth.FirebaseUser


interface FirebaseAuthRepository {
    /**
     * Logs in the user with the given phone number and verification code.
     *
     * @param phoneNumber The phone number of the user to log in.
     * @param verificationCode The verification code received by the user.
     * @param callback The callback to be invoked when the login is complete.
     */
    suspend fun loginWithPhoneNumber(
        phoneNumber: String?,
        verificationCode: String?,
        callback: AuthCallback?
    )

    suspend fun sendVerificationCode(phoneNumber: String): Boolean
    /**
     * Callback interface for handling authentication events.
     */
    interface AuthCallback {
        /**
         * Called when the login is successful.
         *
         * @param user The authenticated user.
         */
        fun onLoginSuccess(user: FirebaseUser?)

        /**
         * Called when the login fails.
         *
         * @param error The error that occurred.
         */
        fun onLoginFailure(error: Exception?)
    }
}