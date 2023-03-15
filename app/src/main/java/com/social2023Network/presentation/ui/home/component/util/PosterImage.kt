package com.social2023Network.presentation.ui.home.component.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.social2023Network.R
import com.social2023Network.util.AllApi

@Composable
fun PosterImage(url: String?, modifier: Modifier, contentScale : ContentScale?) {
    AsyncImage(
        model = url ?: AllApi.DEFAULT_POSTER,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale ?: ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.anime_placeholder)
    )
}