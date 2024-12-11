package com.cekduit.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cekduit.app.data.UserRepository
import com.cekduit.app.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
  fun saveSession(user: UserModel) {
    viewModelScope.launch {
      // Explicitly set isLogin to true when saving the session
      repository.saveSession(user.copy(isLogin = true))
    }
  }
}