package com.example.dicodingstoryapp.data.source.remote

import com.example.dicodingstoryapp.data.source.remote.responses.ApiResponse
import com.example.dicodingstoryapp.data.source.remote.responses.StoryResponse
import com.example.dicodingstoryapp.data.source.remote.services.StoryService
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

    suspend fun addNewStory(description: String, photo: File): Flow<ApiResponse<String>> {
        return flow {
            val descRequestBody = description.toRequestBody("text/plain".toMediaType())
            val photoRequestBody = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())

            val photoMultiPart = MultipartBody.Part.createFormData(
                "photo",
                photo.name,
                photoRequestBody
            )

            when (val response = storyService.createNewStory(
                description = descRequestBody,
                photo = photoMultiPart
            )) {
                is NetworkResponse.Success -> {
                    emit(ApiResponse.Success(response.body.message))
                }
                is NetworkResponse.Error -> {
                    emit(ApiResponse.Error(response.body?.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}