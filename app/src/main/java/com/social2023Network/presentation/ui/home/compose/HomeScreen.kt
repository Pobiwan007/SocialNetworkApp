package com.social2023Network.presentation.ui.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LifecycleCoroutineScope
import com.social2023Network.presentation.ui.home.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TabLayout(viewModel)
    }
}
