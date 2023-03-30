package com.social2023Network.domain.base

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.social2023Network.domain.model.post.Post
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getPosts() : Flow<Result<List<Post>>>
    suspend fun createPost(
        post: Post,
        context: Context,
        listUri: SnapshotStateList<Uri?>,
    )
}