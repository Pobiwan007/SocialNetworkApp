package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import com.social2023Network.domain.model.story.Story
import com.social2023Network.presentation.ui.home.component.util.CircleImage
import com.social2023Network.presentation.ui.home.component.util.ImageResource

@Composable
fun ItemStory(items: List<Story>) {
    Row {
        AddStoryTemplate()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(
                items
            ) { _, item ->
                CardStory(item = item)
            }
        }
    }

}

@Composable
private fun CardStory(item: Story) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier.padding(5.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            CircleImage(url = item.content, 50.dp)
            Text(
                text = item.profile?.secondName ?: "",
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}


@Composable
private fun AddStoryTemplate() {
    Card(
        backgroundColor = colorResource(id = R.color.purple_500),
        modifier = Modifier
            .padding(5.dp)
            .clickable {
            },
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp)
        ) {
            ImageResource(id = R.drawable.baseline_add_circle_outline_24, size = 50.dp) {}
            Text(
                text = stringResource(id = R.string.add_story_title),
                style = TextStyle(fontSize = 15.sp, color = Color.White),
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}