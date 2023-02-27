package com.social2023Network.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.social2023Network.sreens.home.HomeScreen
import com.social2023Network.sreens.MessagesScreen
import com.social2023Network.sreens.ProfileScreen

@Composable
fun Navigation(
    navController: NavHostController,
    title: MutableState<String>,
    isVisibilityTopBar: MutableState<Boolean>
) {
    NavHost(navController, startDestination = NavigationItems.Home.route) {
        composable(NavigationItems.Home.route) {
            HomeScreen(title, isVisibilityTopBar)
        }
        composable(NavigationItems.Messages.route) {
            MessagesScreen(title, isVisibilityTopBar)
        }
        composable(NavigationItems.Profile.route) {
            ProfileScreen(title, isVisibilityTopBar)
        }
    }
}