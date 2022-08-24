package com.example.dicodingstoryapp.domain.usecase.auth

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.domain.model.UserInfo
import com.example.dicodingstoryapp.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: IAuthRepository
): AuthUseCase {
    override fun register(payload: AuthRequest): Flow<Resource<Boolean>> {
        return authRepository.register(payload)
    }

    override fun login(payload: AuthRequest): Flow<Resource<Boolean>> {
        return authRepository.login(payload)
    }

    override fun getUserInfo(): Flow<UserInfo> {
        return authRepository.getUserInfo()
    }
}