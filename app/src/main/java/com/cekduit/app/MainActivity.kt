package com.cekduit.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cekduit.app.databinding.ActivityMainBinding
import com.cekduit.app.ui.components.AddTransactionBottomSheetDialogFragment
import com.cekduit.app.ui.settings.SettingsActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

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
                R.id.navigation_home, R.id.navigation_transactions, R.id.navigation_wallet
            )
        )

        // Setup ActionBar dengan NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val menuItems = listOf(R.id.navigation_home, R.id.navigation_transactions, R.id.navigation_wallet)

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

        val addTransactionFab = binding.floatingActionButton
        addTransactionFab.setOnClickListener {
            val bottomSheet = AddTransactionBottomSheetDialogFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}