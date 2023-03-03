package com.social2023Network.domain.model.story

import com.social2023Network.presentation.ui.home.component.profile.Profile

data class Story(
    val id : Int,
    val dataTime: String,
    val content: String,
    val profile: Profile?= null
)
