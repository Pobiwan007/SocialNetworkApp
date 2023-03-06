package com.social2023Network.common.network

import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.domain.model.weather.WeatherResponse
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

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") location : String,
        @Query("key") apiKey : String
    ): WeatherResponse
}