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
import com.cekduit.app.ui.home.HomeFragment
import com.cekduit.app.ui.settings.SettingsActivity
import com.cekduit.app.ui.welcome.WelcomeActivity
import com.cekduit.app.utils.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupGoogleSignIn()
        setupAddTransactionButton()
        observeSession()
        observeThemeChanges()
        handleIncomingAccountName()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        setSupportActionBar(binding.toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_transactions,
                R.id.navigation_wallet
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupBottomNavigationTransitions(navView, navController)
    }

    private fun setupBottomNavigationTransitions(navView: BottomNavigationView, navController: androidx.navigation.NavController) {
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
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupAddTransactionButton() {
        binding.floatingActionButton.setOnClickListener {
            AddTransactionBottomSheetDialogFragment().show(supportFragmentManager, "AddTransaction")
        }
    }

    private fun observeSession() {
//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        }
    }

    private fun observeThemeChanges() {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            isDarkModeActive?.let { active ->
                AppCompatDelegate.setDefaultNightMode(
                    if (active) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }

    private fun handleIncomingAccountName() {
        val accountName = intent.getStringExtra("ACCOUNT_NAME")
        accountName?.let { name ->
            val bundle = Bundle().apply {
                putString("ACCOUNT_NAME", name)
            }
            val homeFragment = HomeFragment().apply {
                arguments = bundle
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, homeFragment)
                .commit()
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_logout -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}