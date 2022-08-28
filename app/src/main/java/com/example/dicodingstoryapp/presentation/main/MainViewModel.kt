package com.example.dicodingstoryapp.presentation.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.dicodingstoryapp.data.source.local.utils.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {
    val token = sharedPreferences.getString(PreferencesKey.TOKEN_KEY, "") ?: ""
}