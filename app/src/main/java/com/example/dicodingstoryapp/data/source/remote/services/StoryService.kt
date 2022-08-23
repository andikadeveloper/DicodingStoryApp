package com.example.dicodingstoryapp.data.source.remote.services

import com.example.dicodingstoryapp.data.source.remote.responses.ListStoryResponse
import com.example.dicodingstoryapp.data.source.remote.responses.SuccessResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StoryService {
    @GET(value = "stories")
    suspend fun getStories(): ListStoryResponse

    @Multipart
    @POST(value = "stories")
    suspend fun createNewStory(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
    ): SuccessResponse
}