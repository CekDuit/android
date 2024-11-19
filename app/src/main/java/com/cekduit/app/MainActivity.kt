package com.cekduit.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cekduit.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        setSupportActionBar(binding.toolbar)


        // Setup AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_transactions, R.id.navigation_settings
            )
        )

        // Setup ActionBar dengan NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val menuItems = listOf(R.id.navigation_home, R.id.navigation_transactions, R.id.navigation_settings)

        var currentFragmentIndex = 0

        navView.setOnItemSelectedListener { menuItem ->
            val newFragmentIndex = menuItems.indexOf(menuItem.itemId)

            if (newFragmentIndex == currentFragmentIndex) {
                return@setOnItemSelectedListener true
            }

            if (newFragmentIndex != -1) {
                val isNavigatingForward = newFragmentIndex > currentFragmentIndex

                val navOptions = NavOptions.Builder()
                    .setEnterAnim(if (isNavigatingForward) R.anim.slide_in_right else R.anim.slide_in_left)
                    .setExitAnim(if (isNavigatingForward) R.anim.slide_out_left else R.anim.slide_out_right)
                    .build()

                navController.navigate(menuItem.itemId, null, navOptions)
                currentFragmentIndex = newFragmentIndex
                true
            } else {
                false
            }
        }

    }
}