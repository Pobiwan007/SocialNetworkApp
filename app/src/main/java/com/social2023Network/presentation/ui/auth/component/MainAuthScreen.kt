package com.social2023Network.presentation.ui.auth.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.social2023Network.R
import com.social2023Network.presentation.ui.auth.AuthViewModel
import com.social2023Network.presentation.ui.home.component.ApiStateView
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.theme.colorBlueDark
import com.social2023Network.presentation.ui.util.component.TextTitle
import com.social2023Network.presentation.ui.theme.colorRed
import com.social2023Network.presentation.ui.util.component.ImageResource
import com.social2023Network.presentation.ui.util.component.PhoneNumberField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        ImageResource(id = R.drawable.user_auth, size = 200.dp) {}
        Spacer(modifier = Modifier.height(5.dp))
        TextTitle(text = stringResource(id = R.string.auth_text), 4, textAlign = TextAlign.Justify, textColor = colorBlueDark)
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(vertical = 10.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            PhoneNumberField(
                label = stringResource(id = R.string.phone_number_title),
                viewModel = authViewModel
            )
        }
        if (userNameHasError) {
            Text(
                text = "Phone number not available. Please choose a different one.",
                color = colorRed
            )
        }
        OutlinedButton(
            onClick = { CoroutineScope(Dispatchers.IO).launch { authViewModel.sendVerificationCode() } },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.13f)

        ) {
            TextTitle(text = stringResource(id = R.string.submit_number_phone), 1, textColor = colorBlueDark)
        }
    }
}