package com.example.dicodingstoryapp.data.source.local

import com.example.dicodingstoryapp.data.source.local.entity.StoryEntity
import com.example.dicodingstoryapp.data.source.local.room.StoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryLocalSource @Inject constructor(
    private val storyDao: StoryDao
) {
    fun getAllStory(): Flow<List<StoryEntity>> = storyDao.getAllStory()

    suspend fun insertStory(stories: List<StoryEntity>) = storyDao.insertStory(stories)
}