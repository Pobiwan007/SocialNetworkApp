package com.social2023Network.util

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val e: Throwable): ApiState()
    class Success(val data: Any): ApiState()
    object Empty : ApiState()
}
