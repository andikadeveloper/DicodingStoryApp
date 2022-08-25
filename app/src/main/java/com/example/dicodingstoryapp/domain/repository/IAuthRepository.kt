package com.example.dicodingstoryapp.domain.repository

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun register(payload: AuthRequest): Flow<Resource<String>>
    fun login(payload: AuthRequest): Flow<Resource<String>>
    fun getUserInfo(): Flow<UserInfo>
}