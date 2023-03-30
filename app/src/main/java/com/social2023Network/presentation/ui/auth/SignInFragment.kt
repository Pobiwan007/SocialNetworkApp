package com.social2023Network.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.util.component.PhoneNumberField

class SignInFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Background {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)) {
                            PhoneNumberField(
                                textField = remember { mutableStateOf("") },
                                label = "Phone number",
                                onCountryCodeDetected = { countryCode ->
                                    // TODO: Handle the country code detection here.
                                    // Example:
                                    // toast("Detected country code: $countryCode")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}