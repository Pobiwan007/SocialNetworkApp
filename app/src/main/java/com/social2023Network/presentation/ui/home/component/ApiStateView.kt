package com.social2023Network.presentation.ui.home.component

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import com.social2023Network.util.ApiState
import com.social2023Network.presentation.ui.theme.coral
import com.social2023Network.presentation.ui.theme.green

@Composable
fun ApiStateView(
    apiState: ApiState,
    onSuccessResult: @Composable () -> Unit,
    onErrorResult: () -> Unit
) = when (apiState) {
    is ApiState.Loading ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                exit = shrinkOut(shrinkTowards = Alignment.Center) + fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(70.dp)
                        .padding(5.dp)
                        .wrapContentSize(align = Alignment.Center),
                    color = Color.White
                )
            }
        }
    is ApiState.Failure -> {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Mistake: ${apiState.e.message}",
                fontSize = 20.sp,
                style = TextStyle(color = coral),
            )
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.4f)
                    .clickable {
                        onErrorResult()
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = 10.dp
            ) {
                Text(
                    text = stringResource(id = R.string.try_again),
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = green,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(5.dp),
                )
            }
        }


    }
    is ApiState.Success -> {
        onSuccessResult()
    }
    ApiState.Empty -> {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.empty_data_from_network),
                style = TextStyle(fontSize = 25.sp, color = green, textAlign = TextAlign.Center),
            )
        }
    }
}
