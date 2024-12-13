package com.cekduit.app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class SettingsPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val themeKey = booleanPreferencesKey("dark_mode_setting")
    private val reminderKey = booleanPreferencesKey("reminder_setting")
    private val token = stringPreferencesKey("token")
    private val email = stringPreferencesKey("email")
    private val name = stringPreferencesKey("name")

    fun getThemeSetting(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[themeKey]
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[token]
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[this.token] = token
        }
    }

    fun getEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[email]
        }
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[this.email] = email
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[name] ?: ""
        }
    }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[this.name] = name
        }
    }

    fun getReminderStatus(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
          preferences[reminderKey] == true
        }
    }

    suspend fun saveReminderSetting(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[reminderKey] = status
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}