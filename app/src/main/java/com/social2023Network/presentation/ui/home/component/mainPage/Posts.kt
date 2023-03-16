package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.social2023Network.domain.model.post.Post
import com.social2023Network.presentation.ui.home.component.util.TextDesc
import com.social2023Network.presentation.ui.home.component.util.TextTitle

@Composable
fun Posts(post: Post) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .background(Color.White.copy(0.5f)),
        elevation = 5.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitle(text = post.title)
            TextDesc(text = post.desc)
        }
    }
}