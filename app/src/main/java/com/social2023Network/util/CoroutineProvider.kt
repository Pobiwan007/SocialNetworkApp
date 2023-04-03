package com.social2023Network.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface CoroutineProvider {

    fun <T> flowOnIO(block: suspend () -> T): Flow<T> =
        flow {
            emit(withContext(Dispatchers.IO) { block() })
        }.flowOn(Dispatchers.Main)
}