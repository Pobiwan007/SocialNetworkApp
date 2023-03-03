package com.social2023Network.presentation.ui.home.component.animePage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import coil.compose.AsyncImage
import com.social2023Network.domain.model.anime.AnimeEntity
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.theme.pink

@Composable
fun AnimePost(card: AnimeEntity, viewModel: HomeViewModel) {

    var result by remember { mutableStateOf("") }

    LaunchedEffect(viewModel) {
        result = viewModel.onDateReceived(card.attributes?.createdAt.toString())
    }
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(300.dp)
    ) {

        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            AsyncImage(
                model = card.attributes?.coverImage?.original,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.anime_placeholder)
            )

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = card.attributes?.posterImage?.small,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(start = 1.dp),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = card.attributes?.canonicalTitle.toString(),
                        style = MaterialTheme.typography.h6)
                    Text(
                        text = result,
                        style = TextStyle(fontSize = 10.sp)
                    )
                }

            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = card.attributes?.synopsis.toString(),
                style = MaterialTheme.typography.body1,
                maxLines = 2
            )
        }
        PopularityRank(rank = card.attributes?.popularityRank!!)

    }
}

@Composable
fun PopularityRank(rank: Int) {
    Row(horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(end = 10.dp, top = 10.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = "Star icon",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = rank.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = pink,
        )
    }
}