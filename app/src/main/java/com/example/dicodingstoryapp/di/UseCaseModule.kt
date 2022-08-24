package com.example.dicodingstoryapp.di

import com.example.dicodingstoryapp.domain.usecase.auth.AuthInteractor
import com.example.dicodingstoryapp.domain.usecase.auth.AuthUseCase
import com.example.dicodingstoryapp.domain.usecase.story.StoryInteractor
import com.example.dicodingstoryapp.domain.usecase.story.StoryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideStoryUseCase(storyInteractor: StoryInteractor): StoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideAuthUseCase(authInteractor: AuthInteractor): AuthUseCase

}