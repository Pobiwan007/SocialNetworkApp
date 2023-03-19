package com.social2023Network.data.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.social2023Network.presentation.ui.util.DialogManager
import javax.inject.Inject

class PermissionsManager(
    private val fragment: Fragment,
    private val dialogManager: DialogManager
    ) {
    private var _location : Location ?= null


    private val requiredPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private var imageSelectionCallback: ((Uri?) -> Unit)? = null
    private val pickImage = fragment.registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageSelectionCallback?.invoke(uri)
    }

    private val locationPermissionLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestLocation()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocation()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun checkPermissions() {
        val permissionStatus = requiredPermissions.map { permission ->
            ContextCompat.checkSelfPermission(fragment.requireContext(), permission)
        }
        if (permissionStatus.contains(PackageManager.PERMISSION_DENIED)) {
            if(fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                dialogManager.showPermissionSettingsDialog(fragment.requireContext())
            } else
                 requestPermissions()
        } else {
            openGallery()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(fragment.requireActivity(), requiredPermissions, REQUEST_PERMISSION_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions()
            } else {
                // Permission denied permanently, show a message or prompt the user to go to app settings
            }
        }
    }


    private fun openGallery() {
        pickImage.launch("image/*")
    }

    fun selectImage(callback: (Uri?) -> Unit) {
        imageSelectionCallback = callback
        checkPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressWarnings("MissingPermission")
    private fun requestLocation() {
        val locationManager =
            fragment.requireActivity().getSystemService(LocationManager::class.java)
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            Log.e("GPS", location.toString())
            this._location = location
        }
    }

    fun getLocation() = _location

    companion object{
        private const val REQUEST_PERMISSION_CODE = 100
    }
}