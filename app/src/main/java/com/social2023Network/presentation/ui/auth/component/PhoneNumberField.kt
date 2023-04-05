package com.social2023Network.presentation.ui.util.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.social2023Network.domain.transformations.PhoneNumberVisualTransformation
import com.social2023Network.presentation.ui.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneNumberField(
    modifier: Modifier = Modifier,
    label: String? = null,
    viewModel: AuthViewModel,
) {
    TextField(
        value = viewModel.phoneNumber,
        modifier = modifier,
        label = { label?.let { Text(text = it) } },
        onValueChange = {
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.updatePhoneNumber(it)
            }
        },
        singleLine = true,
        leadingIcon = {
            AsyncImage(
                model = viewModel.countryFlagIcon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.25f),
                contentScale = ContentScale.Crop
            )
        },
        visualTransformation = PhoneNumberVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

