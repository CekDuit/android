package com.cekduit.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cekduit.app.utils.SettingsPreference

class MainViewModel(private val pref: SettingsPreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean?> {
        return pref.getThemeSetting().asLiveData()
    }
}