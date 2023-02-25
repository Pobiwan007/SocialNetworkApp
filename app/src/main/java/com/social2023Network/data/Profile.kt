package com.social2023Network.data

data class Profile(
    val id: Int,
    val name: String,
    val secondName: String,
    val birth: String ?= null,
    val avatar: String ?= null,
    val timeLastOnline: String
)
