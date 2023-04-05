package com.social2023Network.domain.usecase

import com.social2023Network.domain.model.countries.CountriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthUseCase {
    suspend fun getCountryFlagIconByNumberCode(
        numberCode: String,
        countriesResponse: List<CountriesResponse>
    ): String = withContext(Dispatchers.Default) {
        var countyFlagID = ""
        countriesResponse.forEach { country ->
            country.idd?.suffixes?.forEach {
                if(country.idd!!.root + it == numberCode)
                    countyFlagID = country.flags?.png!!
            }
        }
        countyFlagID
    }

    fun isPhoneNumberAvailable(input: String): Boolean {
        return (input.isNotEmpty() && input.first() == '+' && !input.contains('#') && !input.contains(
            '*'
        ) && !input.contains(',') && !input.contains('.'))
    }
}