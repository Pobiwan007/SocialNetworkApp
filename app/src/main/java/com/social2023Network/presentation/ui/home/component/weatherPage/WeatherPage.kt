package com.social2023Network.presentation.ui.home.component.weatherPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import com.social2023Network.common.AllApi
import com.social2023Network.domain.model.weather.Forecastday
import com.social2023Network.domain.model.weather.Hour
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.ApiStateView
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.home.component.util.CircleImage
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardCurrentWeather(weatherApiData)
                LazyRow() {
                    try {
                        items(weatherApiData.forecast?.forecastday?.get(0)!!.hour) {
                            CardHour(hour = it)
                        }
                    } catch (e: java.lang.IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    try {
                        items(weatherApiData.forecast?.forecastday!!) {
                            CardDay(it)
                        }
                    } catch (e: java.lang.IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                }

            }
        }) {
        }
    }
}

@Composable
fun CardCurrentWeather(weatherApiData: WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(pink),
        elevation = 5.dp,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherParameters(
                    iconId = R.drawable.baseline_location_on_24,
                    text = weatherApiData.location?.region.toString(),
                    fontSize = 25.sp
                )
                Text(
                    text = (weatherApiData.current?.lastUpdated
                        ?: stringResource(id = R.string.no_info)).substringAfterLast(" "),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 30.sp,
                        fontWeight = FontWeight(1),
                        color = colorBlue
                    ),
                )
            }

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
                    text = (weatherApiData.current?.tempC ?: "0.0") + AllApi.TEMP,
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
                    url = AllApi.HTTPS + weatherApiData.current?.condition?.icon,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(100.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
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

@Composable
fun CardHour(hour: Hour) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .alpha(.7f), elevation = 5.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = hour.time!!.substringAfterLast(" "), style = TextStyle(fontSize = 20.sp))
            CircleImage(url = AllApi.HTTPS + hour.condition!!.icon, dp = 50.dp)
            Text(text = (hour.tempC ?: "0.0") + AllApi.TEMP, style = TextStyle(fontSize = 20.sp))
        }
    }
}

@Composable
fun CardDay(forecastDay: Forecastday) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .alpha(.7f), elevation = 5.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Column() {
                Text(
                    text = forecastDay.date ?: stringResource(id = R.string.no_info),
                    style = TextStyle(fontSize = 20.sp)
                )
                Text(
                    text = (forecastDay.day?.condition?.text
                        ?: stringResource(id = R.string.no_info)),
                    style = TextStyle(fontSize = 12.sp),
                )
            }
            Row(modifier = Modifier.padding(5.dp), horizontalArrangement = Arrangement.End) {
                CircleImage(url = AllApi.HTTPS + forecastDay.day!!.condition!!.icon, dp = 30.dp)
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = (forecastDay.day?.maxtempC
                        ?: stringResource(id = R.string.no_info)) + AllApi.TEMP,
                    style = TextStyle(fontSize = 20.sp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = (forecastDay.day?.mintempC
                        ?: stringResource(id = R.string.no_info)) + AllApi.TEMP,
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.DarkGray
                )
            }
        }
    }

}