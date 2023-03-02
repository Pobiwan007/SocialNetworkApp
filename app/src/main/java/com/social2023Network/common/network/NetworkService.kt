package com.social2023Network.common.network

import com.social2023Network.network.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("anime")
    suspend fun getAnimeWithFilter(
        @Query("filter[text]") filter: String
    ) : AnimeResponse

    @GET("anime")
    suspend fun getAnimeWithFilterCategories(
        @Query("filter[categories]") category: String
    ) : AnimeResponse
}