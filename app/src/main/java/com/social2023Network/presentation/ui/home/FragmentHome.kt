package com.social2023Network.presentation.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.social2023Network.data.permissions.PermissionsManager
import com.social2023Network.presentation.ui.util.DialogManager
import com.social2023Network.presentation.ui.home.component.HomeScreen
import com.social2023Network.util.AllApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentHome : Fragment() {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var permissionsManager: PermissionsManager
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    @Inject lateinit var dialogManager: DialogManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setContext(requireContext())
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen(
                    viewModel
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLocationToViewModel()
        viewModel.permissionsManager = permissionsManager
    }

    private fun setLocationToViewModel(){
        permissionsManager = PermissionsManager(this, dialogManager)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionsManager.requestLocationPermission()
            val location = permissionsManager.getLocation()
            if( location != null)
                viewModel.setLocation("${location.latitude}, ${location.longitude}")
            else
                viewModel.setLocation(AllApi.DEFAULT_CITY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionsManager.onRequestPermissionsResult(requestCode, grantResults)
    }
}

//interface HomeFragmentContract {
//    interface View {
//        fun showLocationPermissionDialog()
//        fun requestLocationPermission()
//        fun setScreenContent(content: @Composable () -> Unit)
//    }
//
//    interface Presenter {
//        fun onViewCreated()
//        fun onLocationPermissionResult(granted: Boolean)
//        fun onLocationReceived(location: String)
//        fun onError(error: Throwable)
//    }
//
//    interface Interactor {
//        fun getCurrentLocation(): String
//    }
//
//    interface Router {
//        fun navigateToDetailScreen()
//    }
//}
