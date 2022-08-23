package com.example.dicodingstoryapp.domain.usecase

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
    fun getAllStory(): Flow<Resource<List<Story>>>
    fun addNewStory(): Flow<Resource<Boolean>>
}