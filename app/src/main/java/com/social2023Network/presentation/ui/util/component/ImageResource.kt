package com.social2023Network.presentation.ui.util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ImageResource(id: Int, size: Dp, onImageClicked: () -> Unit) {
    val clickable = onImageClicked != {}
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = if (clickable) Modifier
            .size(size)
            .padding(5.dp)
            .clickable {
                onImageClicked()
            }
        else Modifier
            .size(size)
            .padding(5.dp)
    )
}