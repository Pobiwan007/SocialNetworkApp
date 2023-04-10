package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.social2023Network.domain.model.post.Post
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.animePage.PostItemRow
import com.social2023Network.presentation.ui.home.component.util.PosterImage
import com.social2023Network.presentation.ui.util.component.TextDesc
import com.social2023Network.presentation.ui.util.component.TextTitle

@Composable
fun Posts(post: Post, homeViewModel: HomeViewModel) {
    val convertedDate = remember { mutableStateOf("") }

    val imageUrlMap by homeViewModel.imagesUrlMap.collectAsState()

    LaunchedEffect(post.time) {
        convertedDate.value = homeViewModel.convertDateTime(post.time!!)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable(onClick = { }),
        elevation = 5.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TextTitle(text = post.title ?: "")
            Spacer(modifier = Modifier.height(2.dp))
            PostItemRow(
                urlImage = null,
                textTitle = post.author ?: "Anya",
                textDate = convertedDate.value
            )
            Spacer(modifier = Modifier.height(2.dp))

            if (post.images?.isNotEmpty() == true) {
                TextDesc(text = post.desc ?: "")
                Spacer(modifier = Modifier.height(8.dp))
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(post.images!!) { nameFile ->
                    val imageUrl = remember { mutableStateOf("") }

                    if (imageUrlMap.containsKey(nameFile))
                        imageUrl.value = imageUrlMap[nameFile].toString()
                    else
                        LaunchedEffect(nameFile) {
                            imageUrl.value = homeViewModel.getUrlImageByFileName(nameFile)
                        }
                    if(imageUrl.value != "")
                    PosterImage(
                        url = imageUrl.value,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(horizontal = 5.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
    }
}
