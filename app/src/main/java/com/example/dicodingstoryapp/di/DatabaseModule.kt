package com.example.dicodingstoryapp.di

import android.content.Context
import androidx.room.Room
import com.example.dicodingstoryapp.data.source.local.room.StoryDao
import com.example.dicodingstoryapp.data.source.local.room.StoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideStoryDatabase(@ApplicationContext context: Context): StoryDatabase {
        return Room.databaseBuilder(
            context,
            StoryDatabase::class.java,
            "story.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideStoryDao(database: StoryDatabase): StoryDao = database.storyDao()
}