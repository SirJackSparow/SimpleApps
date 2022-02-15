package com.startup.simpleapps.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("tokens")

class DataStorePreferences(context: Context) {
    private val dataStore = context.dataStore

    val getToken: Flow<String>
        get() = dataStore.data.map { p ->
            p[TOKEN] ?: ""
        }

    suspend fun setToken(value: String) {
        dataStore.edit { p ->
            p[TOKEN] = value
        }
    }

    val getUsername: Flow<String>
        get() = dataStore.data.map { p ->
            p[USERNAME] ?: ""
        }

    suspend fun setUserName(value: String) {
        dataStore.edit { p ->
            p[USERNAME] = value
        }
    }

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val USERNAME = stringPreferencesKey("name")
    }
}
