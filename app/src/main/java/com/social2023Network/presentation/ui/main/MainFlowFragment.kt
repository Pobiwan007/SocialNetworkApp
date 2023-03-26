package com.social2023Network.presentation.ui.main


import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.social2023Network.R
import com.social2023Network.databinding.FragmentMainFlowBinding
import com.social2023Network.presentation.base.BaseFlowFragment


class MainFlowFragment : BaseFlowFragment(
    R.layout.fragment_main_flow,
    R.id.main_container_view
) {
    private val binding by viewBinding(FragmentMainFlowBinding::bind)

    override fun setupNavigation(navController: NavController) {
        binding.bottomNavView.setupWithNavController(navController)
    }
}