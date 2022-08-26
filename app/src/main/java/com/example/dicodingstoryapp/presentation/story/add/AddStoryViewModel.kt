package com.example.dicodingstoryapp.presentation.story.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

sealed class UiEvent {
    object Loading: UiEvent()
    data class Error(val message: String): UiEvent()
    data class Success(val message: String): UiEvent()
}

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase
): ViewModel() {
    private val _event = Channel<UiEvent>(
        Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun addNewStory(description: String, photo: File) {
        viewModelScope.launch {
            storyUseCase.addNewStory(
                description = description,
                photo = photo,
            ).collect {
                when (it) {
                    is Resource.Loading -> _event.send(UiEvent.Loading)
                    is Resource.Success -> _event.send(UiEvent.Success(it.data))
                    is Resource.Error -> _event.send(UiEvent.Error(it.message))
                }
            }
        }
    }
}