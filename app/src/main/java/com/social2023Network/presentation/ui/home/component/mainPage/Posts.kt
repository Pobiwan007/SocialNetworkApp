package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.social2023Network.domain.model.post.Post
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.util.TextDesc
import com.social2023Network.presentation.ui.home.component.util.TextTitle

@Composable
fun Posts(post: Post, homeViewModel: HomeViewModel) {
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
            TextTitle(text = post.title ?: "")
            TextDesc(text = post.desc ?: "")
            if(post.images?.isNotEmpty() == true){
                LazyRow{
                    items(post.images!!){nameFile ->
                        val imageUrl = remember { mutableStateOf("") }
                        LaunchedEffect(nameFile) {
                            imageUrl.value = homeViewModel.getUrlImageByFileName(nameFile)
                        }
                        if(imageUrl.value != "")
                        AsyncImage(
                            model = imageUrl.value,
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp)
                                .alpha(0.4f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}