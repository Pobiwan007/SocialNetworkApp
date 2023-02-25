package com.social2023Network.itemsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.social2023Network.data.Story
import com.social2023Network.ui.colorRed

@Composable
fun ItemStory(items: List<Story>){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(colorRed)
    ){
        itemsIndexed(
            items
        ){ _, item ->
            CardStory(item = item)
        }
    }
}

@Composable
private fun CardStory(item: Story){
    Card(backgroundColor = Color.White, modifier = Modifier.padding(5.dp)) {
        Column(verticalArrangement = Arrangement.Center) {
            Image(painter = , contentDescription = "content")
        }
    }
}