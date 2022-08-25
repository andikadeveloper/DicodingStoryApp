package com.example.dicodingstoryapp.data.repository

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.data.source.local.AuthLocalSource
import com.example.dicodingstoryapp.data.source.remote.AuthRemoteSource
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.data.source.remote.responses.ApiResponse
import com.example.dicodingstoryapp.domain.repository.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authRemoteSource: AuthRemoteSource,
    private val authLocalSource: AuthLocalSource,
): IAuthRepository {
    override fun register(payload: AuthRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)

            when (val result = authRemoteSource.register(payload).first()) {
                is ApiResponse.Error -> emit(Resource.Error(result.message))
                is ApiResponse.Success -> emit(Resource.Success(result.data))
            }
        }
    }

    override fun login(payload: AuthRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)

            when (val result = authRemoteSource.login(payload).first()) {
                is ApiResponse.Error -> emit(Resource.Error(result.message))
                is ApiResponse.Success -> {
                    authLocalSource.saveUserInfo(result.data.userInfo)
                    emit(Resource.Success(result.data.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getUserInfo() = authLocalSource.getUserInfo()
}