package com.social2023Network.presentation.ui.auth.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.social2023Network.presentation.ui.auth.AuthViewModel
import com.social2023Network.presentation.ui.home.component.ApiStateView
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.theme.colorRed
import com.social2023Network.presentation.ui.util.component.PhoneNumberField

@Composable
fun MainAuthScreen(authViewModel: AuthViewModel) {
    val apiResponseState = authViewModel.countryResponseApiState.collectAsState()

    Background {
        ApiStateView(
            apiState = apiResponseState.value,
            onSuccessResult = { AuthScreen(authViewModel) },
            onErrorResult = {}
        )
    }
}

@Composable
fun AuthScreen(authViewModel: AuthViewModel) {
    val userNameHasError by authViewModel.userNameHasError.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 10.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            PhoneNumberField(
                label = "Phone number",
                viewModel = authViewModel
            )
        }
        if (userNameHasError) {
            Text(
                text = "Phone number not available. Please choose a different one.",
                color = colorRed
            )
        }
    }
}