package com.cekduit.app.di

import android.content.Context
import com.cekduit.app.data.UserRepository
import com.cekduit.app.data.pref.UserPreference
import com.cekduit.app.utils.SettingsPreference
import com.cekduit.app.utils.dataStore

object Injection {
    fun provideSettingPreference(context: Context): SettingsPreference {
        return SettingsPreference.getInstance(context.dataStore)
    }
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}