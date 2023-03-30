package com.social2023Network.presentation.ui.home.component.util

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.social2023Network.util.AllApi

@Composable
fun CircleImage(url: String?, dp: Dp) {
    AsyncImage(
        model = url ?: AllApi.DEFAULT_POSTER,
        contentDescription = null,
        modifier = Modifier
            .size(dp)
            .clip(CircleShape)
            .padding(start = 1.dp),
        contentScale = ContentScale.Crop,
    )
}