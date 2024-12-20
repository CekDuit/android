package com.cekduit.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cekduit.app.utils.SettingsPreference

class HomeViewModel(private val settingsPreference: SettingsPreference) : ViewModel() {

    fun getDisplayName(): LiveData<String> {
        return settingsPreference.getName().asLiveData()
    }
}