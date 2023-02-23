package com.social2023Network.sreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.social2023Network.R
import com.social2023Network.navigation.BottomNavBar
import com.social2023Network.navigation.Navigation
import com.social2023Network.navigation.TopBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(navController) },
        content = {
            Box(modifier = Modifier.padding(it)){
                Navigation(navController = navController)
            }
        },
        backgroundColor = colorResource(id = R.color.white)
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}