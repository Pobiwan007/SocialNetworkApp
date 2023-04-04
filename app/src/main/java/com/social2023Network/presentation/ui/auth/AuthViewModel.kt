package com.social2023Network.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.data.repository.FirebaseAuthRepositoryImpl
import com.social2023Network.domain.model.countries.CountriesResponse
import com.social2023Network.domain.usecase.AuthUseCase
import com.social2023Network.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl,
    private val authUseCase: AuthUseCase
    ) : ViewModel() {
    private var _countryResponseApiState: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    private var _mutableCountryResponseData: MutableStateFlow<List<CountriesResponse>> =
        MutableStateFlow(listOf())

    val countryResponseApiState = _countryResponseApiState.asStateFlow()

    init {
        getCountries()
    }

    private fun getCountries() = viewModelScope.launch {
        _countryResponseApiState.value = ApiState.Loading
        firebaseAuthRepositoryImpl.getCountries()
            .catch {
                _countryResponseApiState.value = ApiState.Failure(it)
            }
            .flowOn(Dispatchers.IO)
            .collectLatest {
                _countryResponseApiState.value = ApiState.Success(it)
                _mutableCountryResponseData.value = it
            }
    }

    suspend fun getCountryFlagIconByNumberCode(numberCode: Int): String = withContext(Dispatchers.Default){
        authUseCase.getCountryFlagIconByNumberCode(numberCode)
    }

}