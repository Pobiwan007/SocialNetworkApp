package com.social2023Network.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ValueEventListener
import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.util.AllApi
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.data.network.RetrofitClient
import com.social2023Network.domain.RealtimeDatabaseRepository
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.presentation.ui.util.DialogManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class HomeRepository @Inject constructor(
        private val firebaseManager: FirebaseManager,
        private val homeUseCase: HomeUseCase,
        private val dialogManager: DialogManager
    ) : RealtimeDatabaseRepository{
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


    override suspend fun getPosts() = callbackFlow<Result<List<Post>>> {
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val items = snapshot.children.map {
                        it.getValue(Post::class.java)
                    }
                    this@callbackFlow.trySendBlocking(Result.success(items.filterNotNull()))
                } catch (e: DatabaseException){
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

    override suspend fun createPost(post: Post, context: Context) {
        try {
            val postId = firebaseManager.getDatabaseReference("posts").push().key ?: throw Exception("Failed to get new post ID")
            post.id = postId

            // Convert Uri list to String list
            val imagePaths = post.images?.map { uri ->
                val fileExt = homeUseCase.convertUriToFileExtension(uri.toUri(), context)
                val file = File(uri.toUri().path!!)
                val storageRef = firebaseManager.getStorageReference("posts").child("${System.currentTimeMillis()}.$fileExt")
                val uploadTask = storageRef.putFile(Uri.fromFile(file))
                uploadTask.addOnProgressListener { taskSnapshot ->
                    dialogManager.uploadFilesWithProgressDialog(context, taskSnapshot)
                }
                // Await for the upload to finish and return the image path
                uploadTask.await()
                storageRef.path
            }
            post.images = imagePaths

            firebaseManager.getDatabaseReference("posts").child(postId).setValue(post).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}