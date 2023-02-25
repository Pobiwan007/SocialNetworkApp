package com.social2023Network.sreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.social2023Network.data.Profile
import com.social2023Network.data.Story
import com.social2023Network.itemsList.ItemStory
import com.social2023Network.navigation.NavigationItems
import com.social2023Network.navigation.TopBar

@Composable
fun HomeScreen() {
    TopBar(NavigationItems.Home.title)
    Column(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        ItemStory(
            items = listOf(
                Story(
                    0,
                    "17.02.2023:9:00",
                    "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
                    profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
                ),
                Story(
                    0,
                    "17.02.2023:9:00",
                    "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
                    profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
                ),
                Story(
                    0,
                    "17.02.2023:9:00",
                    "https://vsegda-pomnim.com/uploads/posts/2022-04/1649130983_5-vsegda-pomnim-com-p-prirodnii-landshaft-foto-6.jpg",
                    profile = Profile(1, "Abdu", "Rakhimov", timeLastOnline = "")
                ),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
