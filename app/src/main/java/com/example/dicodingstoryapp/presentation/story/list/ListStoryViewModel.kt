package com.example.dicodingstoryapp.presentation.story.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dicodingstoryapp.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListStoryViewModel @Inject constructor(storyUseCase: StoryUseCase): ViewModel() {
    val stories = storyUseCase.getAllStory(1, 20, false).asLiveData()
}
