package com.cekduit.app.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.cekduit.app.R
import com.cekduit.app.databinding.ActivitySettingsBinding
import com.cekduit.app.utils.ViewModelFactory
import com.google.android.material.color.MaterialColors

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
            MaterialColors.getColor(
                binding.appBarLayout,
                com.google.android.material.R.attr.colorSurface
            )
        )
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initCurrentLocale()
        initThemeSelection()

        binding.localeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.enLocaleButton -> {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en_US")
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
                R.id.idLocaleButton -> {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("in")
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.saveThemeSetting(checkedId == R.id.darkThemeButton)
            setSelectedTheme(checkedId == R.id.darkThemeButton)
            if (checkedId == R.id.darkThemeButton) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
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
        val currentLocale = resources.configuration.locales.get(0)
        if (currentLocale?.language.contentEquals("en")) {
            binding.enLocaleButton.isChecked = true
        } else {
            binding.idLocaleButton.isChecked = true
        }
    }

    private fun initThemeSelection() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            binding.darkThemeButton.isChecked = true
        } else {
            binding.lightThemeButton.isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}