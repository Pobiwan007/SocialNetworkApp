package com.social2023Network.data.remote.story

import com.social2023Network.data.remote.profile.Profile

data class Story(
    val id : Int,
    val dataTime: String,
    val content: String,
    val profile: Profile?= null
)
