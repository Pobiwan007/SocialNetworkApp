package com.social2023Network.fragments.entity

data class Story(
    val id : Int,
    val dataTime: String,
    val content: String,
    val profile: Profile?= null
)
