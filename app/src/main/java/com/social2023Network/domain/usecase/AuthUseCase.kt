package com.social2023Network.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthUseCase {
    suspend fun getCountryFlagIconByNumberCode(numberCode: Int) : String = withContext(Dispatchers.Default){
        ""
    }
}