package com.cekduit.app.ui.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WalletViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Wallet Fragment"
    }
    val text: LiveData<String> = _text
}