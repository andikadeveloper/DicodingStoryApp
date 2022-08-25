package com.example.dicodingstoryapp.data.source.remote

import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.data.source.remote.responses.ApiResponse
import com.example.dicodingstoryapp.data.source.remote.responses.LoginResponse
import com.example.dicodingstoryapp.data.source.remote.services.AuthService
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun register(payload: AuthRequest): Flow<ApiResponse<String>> {
        return flow {
            when (val response = authService.register(payload)) {
                is NetworkResponse.Success -> {
                    emit(ApiResponse.Success(response.body.message))
                }
                is NetworkResponse.Error -> {
                    emit(ApiResponse.Error(response.body?.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(payload: AuthRequest): Flow<ApiResponse<LoginResponse>> {
        return flow {
            when (val response = authService.login(payload)) {
                is NetworkResponse.Success -> {
                    emit(ApiResponse.Success(response.body))
                }
                is NetworkResponse.Error -> {
                    val message = response.body?.message ?: ""

                    emit(ApiResponse.Error(message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}