package com.social2023Network.data.repository

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.UploadTask
import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.util.AllApi
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.data.network.RetrofitClient
import com.social2023Network.domain.RealtimeDatabaseRepository
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.usecase.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firebaseManager: FirebaseManager,
    private val homeUseCase: HomeUseCase,
) : RealtimeDatabaseRepository {
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
        RetrofitClient.retrofitWeather.getForecastWeather(
            location = location,
            apiKey = AllApi.API_KEY
        )
    }

    private inline fun <T> flowOnIO(crossinline block: suspend () -> T): Flow<T> =
        flow {
            emit(withContext(Dispatchers.IO) { block() })
        }


    override suspend fun getPosts() = callbackFlow<Result<List<Post>>> {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val items = snapshot.children.map {
                        it.getValue(Post::class.java)
                    }
                    this@callbackFlow.trySendBlocking(Result.success(items.filterNotNull()))
                } catch (e: DatabaseException) {
                    e.printStackTrace()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }
        }
        firebaseManager.getDatabaseReference(path = "posts")
            .addValueEventListener(postListener)

        awaitClose {
            firebaseManager.getDatabaseReference("posts")
                .removeEventListener(postListener)
        }
    }

    override suspend fun createPost(
        post: Post,
        context: Context,
        listUri: SnapshotStateList<Uri?>,
        callProgressAlertDialog: (listUploadTask: MutableList<UploadTask>) -> Unit
    ) {
        try {
            val postId = firebaseManager.getDatabaseReference("posts").push().key
                ?: throw Exception("Failed to get new post ID")
            post.id = postId

            val listUploadTask = mutableListOf<UploadTask>()
            // Convert Uri list to String list
            if (listUri.isNotEmpty()) {
                val imagePaths = listUri.map { uri ->
                    val fileExt = homeUseCase.convertUriToFileExtension(uri!!, context)
                    val storageRef = firebaseManager.getStorageReference("posts")
                        .child("${System.currentTimeMillis()}.$fileExt")
                    val uploadTask = storageRef.putFile(uri)
                    listUploadTask.add(uploadTask)
                    // Await for the upload to finish and return the image path
                    uploadTask.await()
                    storageRef.path
                }
                callProgressAlertDialog(listUploadTask)
                post.images = imagePaths

            }
            firebaseManager.getDatabaseReference("posts").child(postId).setValue(post).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}