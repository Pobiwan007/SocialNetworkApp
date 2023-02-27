package com.social2023Network.sreens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import com.social2023Network.R

@Composable
fun HomeScreen(title: MutableState<String>, isVisibilityTopBar: MutableState<Boolean>) {
    isVisibilityTopBar.value = false
    val itemsTab = stringArrayResource(id = R.array.tab_items)
    title.value = "Home"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TabLayout(list = itemsTab)
    }
}
