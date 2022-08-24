package com.example.dicodingstoryapp.data.source.remote

import com.example.dicodingstoryapp.data.source.remote.responses.ApiResponse
import com.example.dicodingstoryapp.data.source.remote.responses.StoryResponse
import com.example.dicodingstoryapp.data.source.remote.services.StoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRemoteSource @Inject constructor(
    private val storyService: StoryService
) {
    suspend fun getAllStory(): Flow<ApiResponse<List<StoryResponse>>> {
        return flow {
            try {
                val response = storyService.getStories()
                val stories = response.listStory ?: listOf()

                emit(ApiResponse.Success(stories))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}