package com.example.dicodingstoryapp.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingstoryapp.core.Resource
import kotlinx.coroutines.channels.Channel
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiEvent {
    object Loading: UiEvent()
    data class Error(val message: String): UiEvent()
    object Success: UiEvent()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: AuthUseCase
): ViewModel() {
    private val _event = Channel<UiEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun register(payload: AuthRequest) {
        viewModelScope.launch {
            when (val result = useCase.register(payload).first()) {
                is Resource.Loading -> _event.send(UiEvent.Loading)
                is Resource.Success -> _event.send(UiEvent.Success)
                is Resource.Error -> _event.send(UiEvent.Error(result.message))
            }
        }
    }
}