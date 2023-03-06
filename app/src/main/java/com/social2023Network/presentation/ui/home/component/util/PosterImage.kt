package com.social2023Network.presentation.ui.home.component.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.social2023Network.R
import com.social2023Network.common.AllApi

@Composable
fun PosterImage(url: String?, height: Dp) {
    AsyncImage(
        model = url ?: AllApi.DEFAULT_POSTER,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.anime_placeholder)
    )
}