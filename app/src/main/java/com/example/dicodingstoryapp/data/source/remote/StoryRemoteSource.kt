package com.example.dicodingstoryapp.data.source.remote

import com.example.dicodingstoryapp.data.source.remote.responses.ApiResponse
import com.example.dicodingstoryapp.data.source.remote.responses.StoryResponse
import com.example.dicodingstoryapp.data.source.remote.services.StoryService
import com.haroldadmin.cnradapter.NetworkResponse
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
    suspend fun getAllStory(page: Int, size: Int, isIncludeLocation: Boolean): Flow<ApiResponse<List<StoryResponse>>> {
        return flow {
            when (val response = storyService.getStories(
                page = page,
                size = size,
                location = if (isIncludeLocation) 1 else 0
            )) {
                is NetworkResponse.Success -> {
                    val stories = response.body.listStory ?: listOf()

                    emit(ApiResponse.Success(stories))
                }
                is NetworkResponse.Error -> {
                    val message = response.body?.message ?: ""

                    emit(ApiResponse.Error(message))
                }
            }

        }.flowOn(Dispatchers.IO)
    }
}