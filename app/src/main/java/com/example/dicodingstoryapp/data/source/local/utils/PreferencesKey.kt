package com.example.dicodingstoryapp.data.source.local.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
    val USER_ID_KEY = stringPreferencesKey("user_id")
    val NAME_KEY = stringPreferencesKey("name")
    val TOKEN_KEY = stringPreferencesKey("token")
}