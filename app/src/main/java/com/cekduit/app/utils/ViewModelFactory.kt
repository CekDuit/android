package com.cekduit.app.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cekduit.app.ui.main.MainViewModel
import com.cekduit.app.di.Injection
import com.cekduit.app.ui.home.HomeViewModel
import com.cekduit.app.ui.login.LoginViewModel
import com.cekduit.app.ui.settings.SettingsViewModel
import com.cekduit.app.ui.welcome.WelcomeActivity
import com.cekduit.app.ui.welcome.WelcomeViewModel

class ViewModelFactory private constructor(
    private val settingsPreference: SettingsPreference,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(settingsPreference) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(settingsPreference) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(settingsPreference) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(settingsPreference) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(settingsPreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideSettingPreference(context)
                )
            }.also { instance = it }
        }
    }
}
