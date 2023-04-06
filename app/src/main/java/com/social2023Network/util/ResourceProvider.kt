package com.social2023Network.util

import android.app.Activity
import android.content.Context

interface ResourceProvider {
    fun getActivityReference(): Activity
    fun getActivityContext(): Context
}