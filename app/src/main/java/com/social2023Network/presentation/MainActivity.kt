package com.social2023Network.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.social2023Network.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(coil.base.R.id.title)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)
}
