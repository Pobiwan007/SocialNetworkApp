package com.social2023Network.sreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.social2023Network.R
import com.social2023Network.navigation.BottomNavBar
import com.social2023Network.navigation.Navigation
import com.social2023Network.navigation.TopBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val titleTopBar = remember {
        mutableStateOf("Messages")
    }
    val isVisibilityTopBar = remember {
        mutableStateOf(true)
    }
    Scaffold(
        topBar = { TopBar(title = titleTopBar.value, isVisibilityTopBar.value)},
        bottomBar = { BottomNavBar(navController) },
        content = {
            Box(modifier = Modifier.padding(it)){
                Navigation(navController = navController, titleTopBar, isVisibilityTopBar)
            }
        },
        backgroundColor = colorResource(id = R.color.white)
    )
}
