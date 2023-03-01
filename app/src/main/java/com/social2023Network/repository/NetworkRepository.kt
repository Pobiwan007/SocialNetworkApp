package com.social2023Network.repository

import com.social2023Network.network.entity.AnimeNetworkService

class NetworkRepository(private val animeNetworkService: AnimeNetworkService){
    suspend fun getAnimeData(filter: String){
        animeNetworkService.getAnimeWithFilter(filter)
    }

    suspend fun getAnimeWithCategories(category: String){
        animeNetworkService.getAnimeWithFilterCategories(category)
    }
}