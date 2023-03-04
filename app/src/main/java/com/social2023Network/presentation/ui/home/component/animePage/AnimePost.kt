package com.social2023Network.presentation.ui.home.component.animePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.social2023Network.R
import com.social2023Network.common.AllApi
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
            .height(340.dp)
    ) {

        Column(modifier = Modifier.padding(bottom = 5.dp)) {
            AsyncImage(
                model = card.attributes?.coverImage?.original ?: AllApi.DEFAULT_POSTER,
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
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(3.dp)
                ) {
                    Text(
                        text = card.attributes?.canonicalTitle.toString(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.End
                        ),
                        maxLines = 1
                    )
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
                maxLines = 3
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ComposeButton {
                    viewModel.openUrl(card.attributes?.youtubeVideoId!!)
                }
                Text(
                    text = card.attributes?.ageRating.toString(),
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(
                            color = when (card.attributes?.ageRating.toString()) {
                                "G" -> Color.Green
                                "PG" -> Color.Yellow
                                "PG-13" -> pink
                                "R" -> Color.Red
                                else -> Color.Gray
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

        }
        PopularityRank(rank = card.attributes?.popularityRank!!)

    }
}

@Composable
fun PopularityRank(rank: Int) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(end = 10.dp, top = 10.dp)
    ) {
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

@Composable
fun ComposeButton(
    onClickButton: () -> Unit
) {
    Button(
        onClick = { onClickButton() },
        modifier = Modifier
            .fillMaxWidth(.4f)
            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(4.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(vertical = 5.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 3.dp,
            pressedElevation = 6.dp,
            disabledElevation = 0.dp
        ),
        enabled = true,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_play_circle_24),
                    contentDescription = null
                )
                Text(
                    text = "Youtube",
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    color = Color(0xFFC4302B)
                )
            }
        },
    )
}