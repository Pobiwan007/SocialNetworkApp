package com.social2023Network.sreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.social2023Network.R

@Composable
fun ProfileScreen(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)

    ) {
        Text(
            text = "Profile Page",
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.align(Alignment.CenterHorizontally).clickable {
                navController.navigate(R.id.action_profile_fragment_to_notificationFragment)
            },
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
        )
    }
}

