package com.example.dicodingstoryapp.di

import com.example.dicodingstoryapp.data.source.mapper.StoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideStoryMapper(): StoryMapper {
        return StoryMapper()
    }
}