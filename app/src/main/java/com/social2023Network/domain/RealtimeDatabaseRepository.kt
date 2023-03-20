package com.social2023Network.domain

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.storage.UploadTask
import com.social2023Network.domain.model.post.Post
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseRepository {
    suspend fun getPosts() : Flow<Result<List<Post>>>
    suspend fun createPost(
        post: Post,
        context: Context,
        listUri: SnapshotStateList<Uri?>,
        callProgressAlertDialog: (taskSnapshot: UploadTask.TaskSnapshot) -> Unit
    )
}