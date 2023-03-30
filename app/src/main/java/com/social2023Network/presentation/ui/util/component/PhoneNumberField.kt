package com.social2023Network.presentation.ui.util.component

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.social2023Network.domain.transformations.PhoneNumberVisualTransformation

@Composable
fun PhoneNumberField(
    textField: MutableState<String>,
    modifier: Modifier = Modifier,
    label: String? = null,
    onCountryCodeDetected: (String) -> Unit = {}
) {
    var countryCode: String by remember { mutableStateOf("RU") }
    var formattedNumber: String by remember { mutableStateOf("") }

    TextField(
        value = formattedNumber,
        modifier = modifier,
        label = { label?.let { Text(text = it) } },
        onValueChange = {
            try {
                // Update the formatted phone number
                val rawNumber = PhoneNumberUtils.stripSeparators(it)
                formattedNumber = PhoneNumberUtils.formatNumber(rawNumber, countryCode)

                // If the country code has not been detected yet, try to detect it from the phone number
                if (countryCode.isEmpty()) {
                    val phoneNumberUtil = PhoneNumberUtil.getInstance()
                    val phoneNumber = try {
                        phoneNumberUtil.parse(rawNumber, null)
                    } catch (e: Exception) {
                        null
                    }
                    if (phoneNumber != null) {
                        countryCode = "+${phoneNumber.countryCode}"
                        onCountryCodeDetected(countryCode)
                    }
                }

                // Update the text field value
                textField.value = rawNumber
            }catch (e: NullPointerException){
                e.printStackTrace()
            }
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

