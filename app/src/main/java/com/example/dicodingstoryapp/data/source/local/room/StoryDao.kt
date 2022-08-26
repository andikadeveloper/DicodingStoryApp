package com.example.dicodingstoryapp.data.source.local.room

import androidx.room.*
import com.example.dicodingstoryapp.data.source.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    fun getAllStory(): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoryEntity>)

    @Query("DELETE FROM story")
    suspend fun deleteAllStory()
}