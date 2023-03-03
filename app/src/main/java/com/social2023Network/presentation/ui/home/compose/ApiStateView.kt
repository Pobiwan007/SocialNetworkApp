package com.social2023Network.presentation.ui.home.compose

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.social2023Network.R
import com.social2023Network.common.network.ApiState

@Composable
fun ApiStateView(apiState: ApiState, onSuccessResult: @Composable ()  -> Unit) {
    when (apiState) {
        is ApiState.Loading -> {
            CircularProgressIndicator()
        }
        is ApiState.Failure -> {
            Text(text = "Mistake: ${apiState.e.message}")
        }
        is ApiState.Success -> {
           onSuccessResult()
        }
        ApiState.Empty -> {
            Text(text = stringResource(id = R.string.empty_data_from_network))
        }
    }
}
