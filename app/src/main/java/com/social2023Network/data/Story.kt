package com.social2023Network.data

data class Story(
    val id : Int,
    val dataTime: String,
    val content: String,
    val profile: Profile ?= null
)
