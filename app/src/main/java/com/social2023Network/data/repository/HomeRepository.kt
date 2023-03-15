package com.social2023Network.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.social2023Network.data.firebase.FirebaseDB
import com.social2023Network.util.AllApi
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.data.network.RetrofitClient
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.model.weather.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class HomeRepository(@Inject private val firebaseDB: FirebaseDB){
    suspend fun getAnimeData(): Flow<AnimeResponse> = flowOnIO {
        RetrofitClient.retrofitAnime.getAnime()
    }

    suspend fun getAnimeDataWithSearch(filter: String): Flow<AnimeResponse> = flowOnIO {
        RetrofitClient.retrofitAnime.getAnimeWithFilter(filter)
    }

    suspend fun getAnimeDataWithCategories(category: String): Flow<AnimeResponse> = flowOnIO {
        RetrofitClient.retrofitAnime.getAnimeWithFilterCategories(category)
    }

    suspend fun getCurrentWeather(location: String): Flow<WeatherResponse> = flowOnIO {
        RetrofitClient.retrofitWeather.getForecastWeather(location = location, apiKey = AllApi.API_KEY)
    }
    private inline fun <T> flowOnIO(crossinline block: suspend () -> T): Flow<T> =
        flow {
            emit(withContext(Dispatchers.IO) { block() })
        }

    suspend fun getDataSnapshotFromFirebase(dataPath: String): DataSnapshot? {
        return withContext(Dispatchers.IO) {
            val data = firebaseDB.getDatabaseReference(dataPath).get().await()
            // Parse the data here and return it as a domain model
            data
        }
    }

    suspend fun setNewPostToFirebase(post: Post) {
        return suspendCancellableCoroutine { continuation ->
            val dataReference = firebaseDB.getDatabaseReference("path")
            val dataListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // This is called when the data is successfully written to Firebase
                    continuation.resume(Unit)
                }

                override fun onCancelled(error: DatabaseError) {
                    // This is called if there is an error writing the data to Firebase
                    continuation.resumeWithException(error.toException())
                }
            }

            // Set the new data to Firebase
            dataReference.setValue(post)
                .addOnSuccessListener { _ ->
                    // Listen for changes to the data to know when it's successfully written
                    dataReference.addValueEventListener(dataListener)
                }
                .addOnFailureListener { e ->
                    // If there is an error setting the data, call resumeWithException to return the error
                    continuation.resumeWithException(e)
                }

            // If the coroutine is cancelled, remove the listener to avoid memory leaks
            continuation.invokeOnCancellation {
                dataReference.removeEventListener(dataListener)
            }
        }
    }
}