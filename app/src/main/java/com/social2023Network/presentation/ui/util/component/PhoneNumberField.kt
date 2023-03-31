package com.social2023Network.presentation.ui.util.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.social2023Network.domain.transformations.PhoneNumberVisualTransformation

@Composable
fun PhoneNumberField(
    modifier: Modifier = Modifier,
    label: String? = null,
    onCountryCodeDetected: (String) -> Unit = {}
) {
    var countryCode: String by remember { mutableStateOf("") }
    var formattedNumber: String by remember { mutableStateOf("") }

    TextField(
        value = formattedNumber,
        modifier = modifier,
        label = { label?.let { Text(text = it) } },
        onValueChange = {

        },
        singleLine = true,
        leadingIcon = {
//            if (countryCode.isNotEmpty()) {
//                val flagResId = getFlagResourceForCountryCode(countryCode)
//                Image(
//                    painter = painterResource(flagResId),
//                    contentDescription = null
//                )
//            }
        },
        visualTransformation = PhoneNumberVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

