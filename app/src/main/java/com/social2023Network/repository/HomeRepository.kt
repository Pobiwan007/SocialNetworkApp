package com.social2023Network.repository

import com.social2023Network.network.AnimeResponse
import com.social2023Network.network.NetworkService
import com.social2023Network.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository(private val animeNetworkService: NetworkService){
    suspend fun getAnimeData(filter: String): Flow<AnimeResponse> =  flow{
        val r = RetrofitClient.retrofitAnime.getAnimeWithFilter(filter)
        emit(r)
    }.flowOn(Dispatchers.IO)

    suspend fun getAnimeDataWithCategories(category: String): Flow<AnimeResponse> =  flow{
        val r = RetrofitClient.retrofitAnime.getAnimeWithFilterCategories(category)
        emit(r)
    }.flowOn(Dispatchers.IO)
}