package com.example.dicodingstoryapp.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.dicodingstoryapp.data.source.local.utils.PreferencesKey
import com.example.dicodingstoryapp.data.source.remote.responses.UserInfoResponse
import com.example.dicodingstoryapp.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveUserInfo(userInfo: UserInfoResponse) {
        dataStore.edit { preferences ->
            userInfo.apply {
                preferences[PreferencesKey.NAME_KEY] = name
                preferences[PreferencesKey.USER_ID_KEY] = userId
                preferences[PreferencesKey.TOKEN_KEY] = token
            }
        }
    }

    fun getUserInfo(): Flow<UserInfo> = dataStore.data.map { preferences ->
        val name = preferences[PreferencesKey.NAME_KEY] ?: ""
        val userId = preferences[PreferencesKey.USER_ID_KEY] ?: ""
        val token = preferences[PreferencesKey.TOKEN_KEY] ?: ""

        UserInfo(name = name, userId = userId, token = token)
    }
}