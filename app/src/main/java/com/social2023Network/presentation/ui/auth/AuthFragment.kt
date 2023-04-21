package com.social2023Network.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.social2023Network.R
import com.social2023Network.presentation.extensions.navigateSafely
import com.social2023Network.presentation.ui.auth.component.MainAuthScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AuthFragment: Fragment() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private val viewModel by viewModels<AuthViewModel> { authViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainAuthScreen(authViewModel = viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigateToVerificationFragment()
    }

    private fun initNavigateToVerificationFragment(){
        if(viewModel.isVerifyCodeSend.value)
            findNavController().navigateSafely(R.id.action_authFragment_to_verificationFragment)
    }
}