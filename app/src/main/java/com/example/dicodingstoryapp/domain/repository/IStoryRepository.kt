package com.example.dicodingstoryapp.domain.repository

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.domain.model.Story
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IStoryRepository {
    fun getAllStory(page: Int, size: Int, isIncludeLocation: Boolean): Flow<Resource<List<Story>>>
    fun addNewStory(description: String, photo: File): Flow<Resource<String>>
}