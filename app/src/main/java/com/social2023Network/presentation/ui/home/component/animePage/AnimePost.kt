package com.social2023Network.presentation.ui.home.component.animePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import com.social2023Network.domain.model.anime.AnimeEntity
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.util.*
import com.social2023Network.presentation.ui.util.component.TextCursor
import com.social2023Network.presentation.ui.util.component.TextDesc
import com.social2023Network.presentation.ui.util.component.TextTitle
import com.social2023Network.util.AllApi

@Composable
fun AnimePost(card: AnimeEntity, viewModel: HomeViewModel) {

    val colorRating = remember {
        mutableStateOf(Color.Green)
    }
    LaunchedEffect(viewModel){
        colorRating.value = viewModel.getColorByRatingName(card.attributes?.ageRating.toString())
    }
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(340.dp)
    ) {

        Column(modifier = Modifier.padding(bottom = 5.dp)) {
            PosterImage(
                url = card.attributes?.coverImage?.original,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                null
            )
            PostItemRow(
                urlImage = card.attributes?.posterImage?.small ?: AllApi.DEFAULT_POSTER,
                textTitle = card.attributes?.canonicalTitle.toString(),
                textDate = "${stringResource(R.string.from)} ${card.attributes?.startDate} ${
                    stringResource(R.string.to)} ${card.attributes?.endDate}"
            )
            TextDesc(text = card.attributes?.synopsis.toString())
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ComposeButton {
                    viewModel.openUrl(card.attributes?.youtubeVideoId ?: "qig4KOK2R2g")
                }
                EpisodeCount(count = card.attributes?.episodeCount.toString())
                Text(
                    text = card.attributes?.ageRating ?: "PG-13",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(
                            color = colorRating.value,
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
            color = Color.White,
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

@Composable
fun EpisodeCount(count: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 7.dp,
        content = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Episodes: $count",
                fontSize = 16.sp
            )
        }
    )
}

@Composable
fun PostItemRow(
    urlImage: String?,
    textTitle: String,
    textDate: String
){
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImage(url = urlImage, 50.dp)
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(3.dp)
        ) {
            TextTitle(textTitle, 1)
            TextCursor(
                text = textDate,
            )
        }

    }
}