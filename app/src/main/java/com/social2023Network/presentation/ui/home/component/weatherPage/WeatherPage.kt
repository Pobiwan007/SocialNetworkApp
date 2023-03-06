package com.social2023Network.presentation.ui.home.component.weatherPage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.presentation.ui.home.HomeViewModel

@Composable
fun WeatherPage (viewModel: HomeViewModel){
    val weatherApiData by viewModel.mutableDataWeather.collectAsState(initial = WeatherResponse())
    val weatherApiState by viewModel.apiStateWeather.collectAsState()


}