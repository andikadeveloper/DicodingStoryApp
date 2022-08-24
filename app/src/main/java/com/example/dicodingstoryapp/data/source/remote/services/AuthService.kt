package com.example.dicodingstoryapp.data.source.remote.services

import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.data.source.remote.responses.LoginResponse
import com.example.dicodingstoryapp.data.source.remote.responses.CommonResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(value = "register")
    suspend fun register(@Body payload: AuthRequest): CommonResponse

    @POST(value = "login")
    suspend fun login(@Body payload: AuthRequest): NetworkResponse<LoginResponse, CommonResponse>
}