package com.example.dicodingstoryapp.presentation.story.list

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dicodingstoryapp.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListStoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val stories = storyUseCase.getAllStory(1, 20, false).asLiveData()

    fun logout() {
        sharedPreferences.edit { clear() }
        storyUseCase.deleteAllStory()
    }
}
