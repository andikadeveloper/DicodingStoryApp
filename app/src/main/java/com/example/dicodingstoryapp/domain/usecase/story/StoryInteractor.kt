package com.example.dicodingstoryapp.domain.usecase.story

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.data.repository.StoryRepository
import com.example.dicodingstoryapp.domain.model.Story
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class StoryInteractor @Inject constructor(
    private val storyRepository: StoryRepository
) : StoryUseCase {
    override fun getAllStory(
        page: Int,
        size: Int,
        isIncludeLocation: Boolean
    ): Flow<Resource<List<Story>>> {
        return storyRepository.getAllStory(page, size, isIncludeLocation)
    }

    override fun addNewStory(description: String, photo: File) = storyRepository.addNewStory(
        description = description,
        photo = photo,
    )

    override fun deleteAllStory() = storyRepository.deleteAllStory()
}