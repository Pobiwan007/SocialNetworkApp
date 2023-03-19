package com.social2023Network.domain

import android.content.Context
import com.social2023Network.domain.model.post.Post
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseRepository {
    suspend fun getPosts() : Flow<Result<List<Post>>>
    suspend fun createPost(post: Post, context: Context)
}