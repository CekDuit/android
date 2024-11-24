package com.cekduit.app.ui.settings

import android.app.LocaleManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cekduit.app.R
import com.cekduit.app.databinding.ActivitySettingsBinding
import com.cekduit.app.utils.LocaleSetting
import com.cekduit.app.utils.ViewModelFactory
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Appbar adjustment
        binding.appBarLayout.setStatusBarForegroundColor(
            MaterialColors.getColor(binding.appBarLayout, com.google.android.material.R.attr.colorSurface))
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initCurrentLocale()
        listenOnThemeChanges()

        binding.localeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.enLocaleButton -> {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
                R.id.idLocaleButton -> {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("id")
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.saveThemeSetting(checkedId == R.id.darkThemeButton)
        }
    }

    private fun setSelectedTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            binding.darkThemeButton.isChecked = true
        } else {
            binding.lightThemeButton.isChecked = true
        }
    }

    private fun initCurrentLocale() {
        val currentAppLocales: LocaleList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            applicationContext.getSystemService(LocaleManager::class.java).getApplicationLocales(
                packageName
            )
        } else {
            LocaleList.getDefault()
        }
        val currentLocale = currentAppLocales.get(0)
        if (currentLocale.language == "en") {
            binding.enLocaleButton.isChecked = true
        } else {
            binding.idLocaleButton.isChecked = true
        }
    }

    private fun listenOnThemeChanges() {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            setSelectedTheme(isDarkModeActive)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}