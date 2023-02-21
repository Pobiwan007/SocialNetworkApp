package com.social2023Network.navigation

import com.social2023Network.R

sealed class NavigationItems(var route: String, var icon: Int, var title: String){
    object Home: NavigationItems("home", R.drawable.baseline_home_24, "Home")
    object Messages: NavigationItems("messages", R.drawable.baseline_chat_24, "Messages")
    object Profile: NavigationItems("profile", R.drawable.baseline_person_outline_24, "Profile")
}
