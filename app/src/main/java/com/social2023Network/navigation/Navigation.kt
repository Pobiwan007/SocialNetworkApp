package com.social2023Network.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.social2023Network.sreens.HomeScreen
import com.social2023Network.sreens.MessagesScreen
import com.social2023Network.sreens.ProfileScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController, startDestination = NavigationItems.Home.route) {
        composable(NavigationItems.Home.route) {
            HomeScreen()
        }
        composable(NavigationItems.Messages.route) {
            MessagesScreen()
        }
        composable(NavigationItems.Profile.route) {
            ProfileScreen()
        }
    }
}