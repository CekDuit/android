package com.cekduit.app.di

import android.content.Context
import com.cekduit.app.utils.SettingsPreference
import com.cekduit.app.utils.dataStore

object Injection {
    fun provideSettingPreference(context: Context): SettingsPreference {
        return SettingsPreference.getInstance(context.dataStore)
    }
}