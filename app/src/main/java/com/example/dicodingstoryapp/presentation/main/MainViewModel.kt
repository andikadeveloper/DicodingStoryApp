package com.example.dicodingstoryapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dicodingstoryapp.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    authUseCase: AuthUseCase
): ViewModel() {
    val userInfo = authUseCase.getUserInfo().asLiveData()
}