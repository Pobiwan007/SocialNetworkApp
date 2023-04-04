package com.social2023Network.presentation.ui.auth.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.social2023Network.domain.model.countries.CountriesResponse
import com.social2023Network.presentation.ui.auth.AuthViewModel
import com.social2023Network.presentation.ui.home.component.ApiStateView
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.util.component.PhoneNumberField

@Composable
fun MainAuthScreen(authViewModel: AuthViewModel) {
    val apiResponseState = authViewModel.countryResponseApiState.collectAsState()

    Background {
        ApiStateView(
            apiState = apiResponseState.value,
            onSuccessResult = { AuthScreen() },
            onErrorResult = {}
        )
    }
}

@Composable
fun AuthScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)) {
            PhoneNumberField(
                label = "Phone number",
            )
        }
    }
}