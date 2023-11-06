package com.juanpablo061207.calcunote.data.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(private val settingsDataStore: DataStore<Preferences>) {
    suspend fun saveUserId(userId: String) {
        settingsDataStore.edit {
            it[stringPreferencesKey("user_id")] = userId
        }
    }

    fun getCurrentUserId() =
        settingsDataStore.data.map { it[stringPreferencesKey("user_id")] ?: "" }
}