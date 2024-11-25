package com.cekduit.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cekduit.app.utils.SettingsPreference
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingsPreference) : ViewModel() {
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}