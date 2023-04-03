package com.social2023Network.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.social2023Network.presentation.ui.auth.component.MainAuthScreen
import javax.inject.Inject

class SignInFragment: Fragment() {

    lateinit var viewModel: AuthViewModel
    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                MainAuthScreen(authViewModel = viewModel)
            }
        }
    }
}