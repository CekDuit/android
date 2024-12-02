package com.cekduit.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cekduit.app.data.UserRepository
import com.cekduit.app.data.pref.UserModel
import com.cekduit.app.utils.SettingsPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingsPreference, private val repository: UserRepository) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean?> {
        return pref.getThemeSetting().asLiveData()
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}