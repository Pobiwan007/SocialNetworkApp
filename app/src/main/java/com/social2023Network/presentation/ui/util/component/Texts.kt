package com.social2023Network.presentation.ui.home.component.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.End
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        )
}

@Composable
fun TextDesc(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        maxLines = 3,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
}

@Composable
fun TextCursor(text: String) {
    Text(
        text = text, style = TextStyle(fontSize = 10.sp),
        textDecoration = TextDecoration.Underline
    )
}