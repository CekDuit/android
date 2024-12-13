package com.cekduit.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cekduit.app.utils.SettingsPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val settingsPreference: SettingsPreference): ViewModel() {
    fun saveCredentials(token: String, email: String, name: String) {
        viewModelScope.launch {
            settingsPreference.saveToken(token)
            settingsPreference.saveEmail(email)
            settingsPreference.saveName(name)
        }
    }
}