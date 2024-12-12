package com.cekduit.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cekduit.app.utils.SettingsPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingsPreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean?> {
        return pref.getThemeSetting().asLiveData()
    }
}