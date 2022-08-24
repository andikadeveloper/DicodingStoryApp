package com.example.dicodingstoryapp.data.source.remote.services

import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.data.source.remote.responses.LoginResponse
import com.example.dicodingstoryapp.data.source.remote.responses.SuccessResponse
import retrofit2.http.POST

interface AuthService {
    @POST(value = "register")
    suspend fun register(payload: AuthRequest): SuccessResponse

    @POST(value = "login")
    suspend fun login(payload: AuthRequest): LoginResponse
}