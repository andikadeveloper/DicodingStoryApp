package com.example.dicodingstoryapp.domain.repository

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {
    fun getAllStory(): Flow<Resource<List<Story>>>
    fun addNewStory(): Flow<Resource<Boolean>>
}