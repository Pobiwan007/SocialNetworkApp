package com.social2023Network.presentation.ui.home.component.weatherPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.ApiStateView
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.home.component.util.PosterImage
import com.social2023Network.presentation.ui.theme.colorBlue
import com.social2023Network.presentation.ui.theme.green
import com.social2023Network.presentation.ui.theme.pink

@Composable
fun WeatherPage(viewModel: HomeViewModel) {
    val weatherApiData by viewModel.mutableDataCurrentWeather.collectAsState(initial = WeatherResponse())
    val weatherApiState by viewModel.apiStateCurrentWeather.collectAsState()

    Background {
        ApiStateView(apiState = weatherApiState, onSuccessResult = {
            Column(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(pink)
                        .alpha(0.5f),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 14.dp)
                    ) {
                        WeatherParameters(
                            iconId = R.drawable.baseline_location_on_24,
                            text = weatherApiData.location?.region.toString(),
                            fontSize = 25.sp
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            WeatherParameters(
                                iconId = R.drawable.wind,
                                text = (weatherApiData.current?.windKph ?: "0.0") + "kph"
                            )
                            Text(
                                text = (weatherApiData.current?.tempC ?: "0.0") + "С°",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.End,
                                    color = green
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            ) {

                                WeatherParameters(
                                    iconId = R.drawable.pressure,
                                    text = (weatherApiData.current?.precipMm ?: "0.0") + "Mm"
                                )
                                WeatherParameters(
                                    iconId = R.drawable.humidity,
                                    text = (weatherApiData.current?.humidity ?: "0.0") + "%"
                                )
                            }
                            PosterImage(
                                url = "https:${weatherApiData.current?.condition?.icon}",
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(100.dp),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                }
            }
        }) {
        }
    }
}


@Composable
fun WeatherParameters(iconId: Int, text: String, fontSize: TextUnit? = 16.sp) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = fontSize!!,
                fontWeight = FontWeight(1),
                color = colorBlue
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}