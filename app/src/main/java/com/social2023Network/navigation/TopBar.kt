package com.social2023Network.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.social2023Network.R

@Composable
fun TopBar(title: String){
    TopAppBar (
        title = { Text(text = title, fontSize = 18.sp, textAlign = TextAlign.Center)},
        backgroundColor = colorResource(id = R.color.purple_500),
        contentColor = Color.White
    )
}
