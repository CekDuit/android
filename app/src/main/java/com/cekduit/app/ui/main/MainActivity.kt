package com.cekduit.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cekduit.app.R
import com.cekduit.app.databinding.ActivityMainBinding
import com.cekduit.app.ui.createTransaction.AddTransactionBottomSheetDialogFragment
import com.cekduit.app.ui.settings.SettingsActivity
import com.cekduit.app.ui.welcome.WelcomeActivity
import com.cekduit.app.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        listenOnThemeChanges()

        setSupportActionBar(binding.toolbar)

        // Setup AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_transactions, R.id.navigation_wallet
            )
        )
        // Setup ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val menuItems = listOf(
            R.id.navigation_home,
            R.id.navigation_transactions,
            R.id.navigation_wallet
        )

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

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        setupView()
    }

    private fun setupView() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun listenOnThemeChanges() {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive == null) {
                return@observe
            }
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_logout -> {
                viewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}