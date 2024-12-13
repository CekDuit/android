package com.cekduit.app.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cekduit.app.utils.SettingsPreference

class WelcomeViewModel(private val settingsPreference: SettingsPreference): ViewModel() {
    fun getDisplayName(): LiveData<String> {
        return settingsPreference.getName().asLiveData()
    }
}