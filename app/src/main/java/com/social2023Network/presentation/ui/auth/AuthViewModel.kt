package com.social2023Network.presentation.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social2023Network.data.repository.FirebaseAuthRepositoryImpl
import com.social2023Network.domain.model.countries.CountriesResponse
import com.social2023Network.domain.usecase.AuthUseCase
import com.social2023Network.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl,
    private val authUseCase: AuthUseCase
) : ViewModel() {


    var phoneNumber by mutableStateOf("")
        private set

    var countryFlagIcon by mutableStateOf("")
        private set

    private var _countryResponseApiState: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    private var _mutableCountryResponseData: MutableStateFlow<List<CountriesResponse>> =
        MutableStateFlow(listOf())
    val countryResponseApiState = _countryResponseApiState.asStateFlow()

    var mutableIsVerifyCodeSend = MutableLiveData<Boolean>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val userNameHasError: StateFlow<Boolean> =
        snapshotFlow { phoneNumber }
            .mapLatest { !authUseCase.isPhoneNumberAvailable(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

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
                countryFlagIcon = it.first().flags?.png!!
                phoneNumber = it.first().idd?.root?.plus(it.first().idd!!.suffixes.first())!!
            }
    }

    private suspend fun getCountryFlagIconByNumberCode(numberCode: String) =
        withContext(Dispatchers.Default) {
            val newFlagIconValue = authUseCase.getCountryFlagIconByNumberCode(
                numberCode,
                _mutableCountryResponseData.value
            )
            if (newFlagIconValue.isNotEmpty())
                countryFlagIcon = newFlagIconValue
        }

    suspend fun sendVerificationCode() = withContext(Dispatchers.IO) {
        if ("^\\+\\d{10,13}\$".toRegex().containsMatchIn(phoneNumber))
            mutableIsVerifyCodeSend.value =
                firebaseAuthRepositoryImpl.sendVerificationCode(phoneNumber)
    }

    suspend fun updatePhoneNumber(input: String) {
        try {
            if (authUseCase.isPhoneNumberAvailable(input)) {
                phoneNumber = input
                getCountryFlagIconByNumberCode(input)
            }
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }
}