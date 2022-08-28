package com.example.dicodingstoryapp.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiEvent {
    object Loading : UiEvent()
    data class Error(val message: String) : UiEvent()
    data class Success(val message: String) : UiEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {
    private val _event = Channel<UiEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun login(payload: AuthRequest) {
        viewModelScope.launch {
            useCase.login(payload).collect {
                when (it) {
                    is Resource.Loading -> _event.send(UiEvent.Loading)
                    is Resource.Success -> _event.send(UiEvent.Success(it.data))
                    is Resource.Error -> _event.send(UiEvent.Error(it.message))
                }
            }
        }
    }
}