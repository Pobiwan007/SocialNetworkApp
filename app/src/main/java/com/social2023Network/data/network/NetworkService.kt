package com.social2023Network.data.network

import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.countries.CountriesResponse
import com.social2023Network.domain.model.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("anime")
    suspend fun getAnimeWithFilter(
        @Query("filter[text]") filter: String
    ) : AnimeResponse

    @GET("anime")
    suspend fun getAnime() : AnimeResponse

    @GET("anime")
    suspend fun getAnimeWithFilterCategories(
        @Query("filter[categories]") category: String
    ) : AnimeResponse

    @GET("forecast.json?days=7")
    suspend fun getForecastWeather(
        @Query("q") location : String,
        @Query("key") apiKey : String

    ): WeatherResponse

    @GET("all")
    fun getAllCountries(): Call<List<CountriesResponse>>
}