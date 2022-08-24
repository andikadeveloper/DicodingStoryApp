package com.example.dicodingstoryapp.di

import com.example.dicodingstoryapp.data.repository.AuthRepository
import com.example.dicodingstoryapp.data.repository.StoryRepository
import com.example.dicodingstoryapp.domain.repository.IAuthRepository
import com.example.dicodingstoryapp.domain.repository.IStoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideStoryRepository(storyRepository: StoryRepository): IStoryRepository

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepository): IAuthRepository
}