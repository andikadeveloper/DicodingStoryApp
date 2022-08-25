package com.example.dicodingstoryapp.data.source.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.dicodingstoryapp.data.source.local.utils.PreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveUserToken(token: String) {
        sharedPreferences.edit(commit = true) {
            putString(PreferencesKey.TOKEN_KEY, token)
        }
    }
}