package com.social2023Network.data.repository

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.util.AllApi
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.data.network.RetrofitClient
import com.social2023Network.domain.base.HomeRepository
import com.social2023Network.domain.model.post.Post
import com.social2023Network.domain.model.weather.WeatherResponse
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.presentation.ui.util.DialogManager
import com.social2023Network.util.CoroutineProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firebaseManager: FirebaseManager,
    private val homeUseCase: HomeUseCase,
    private val dialogManager: DialogManager
) : HomeRepository, CoroutineProvider {
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


    override suspend fun getPosts() = callbackFlow<Result<List<Post>>> {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    it.getValue(Post::class.java)
                }
                this@callbackFlow.trySendBlocking(Result.success(items.filterNotNull()))
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
        listUri: SnapshotStateList<Uri?>,
    ) {
        try {
            val postId = firebaseManager.getDatabaseReference("posts").push().key
                ?: throw Exception("Failed to get new post ID")
            post.id = postId
            val storageRef = firebaseManager.getStorageReference("posts")

            // Convert Uri list to String list
            if (listUri.isNotEmpty()) {
                val imagePaths = listUri.map { uri ->
                    val fileExt = homeUseCase.convertUriToFileExtension(uri!!)
                    val fileName = "${System.currentTimeMillis()}.$fileExt"
                    val uploadTask = storageRef.child(fileName).putFile(uri)
                    dialogManager.showProgressDialog(uploadTask)
                    // Await for the upload to finish and return the image path
                    uploadTask.await()
                    fileName
                }
                post.images = imagePaths as MutableList<String>

            }
            firebaseManager.getDatabaseReference("posts").child(postId).setValue(post).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    suspend fun getImageUrlByFileName(fileName: String): String{
        var urlFile = ""
        firebaseManager.getStorageReference("posts").child(fileName).downloadUrl
            .addOnSuccessListener {
                urlFile = it.toString()
            }
            .addOnFailureListener {
                urlFile = AllApi.DEFAULT_POSTER
            }
            .await()
        return urlFile
    }
}