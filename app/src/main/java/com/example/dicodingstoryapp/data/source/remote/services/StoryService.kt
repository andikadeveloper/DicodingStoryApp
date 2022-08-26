package com.example.dicodingstoryapp.data.source.remote.services

import com.example.dicodingstoryapp.data.source.remote.responses.ListStoryResponse
import com.example.dicodingstoryapp.data.source.remote.responses.CommonResponse
import com.haroldadmin.cnradapter.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryService {
    @GET(value = "stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int,
    ): NetworkResponse<ListStoryResponse, CommonResponse>

    @Multipart
    @POST(value = "stories")
    suspend fun createNewStory(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
    ): NetworkResponse<CommonResponse, CommonResponse>
}