package com.example.dicodingstoryapp.domain.usecase.story

import com.example.dicodingstoryapp.data.repository.StoryRepository
import javax.inject.Inject

class StoryInteractor @Inject constructor(
    private val storyRepository: StoryRepository
): StoryUseCase {
    override fun getAllStory() = storyRepository.getAllStory()

    override fun addNewStory() = storyRepository.addNewStory()
}