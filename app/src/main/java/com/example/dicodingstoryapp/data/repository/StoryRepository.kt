package com.example.dicodingstoryapp.data.repository

import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.data.source.local.StoryLocalSource
import com.example.dicodingstoryapp.data.source.mapper.StoryMapper
import com.example.dicodingstoryapp.data.source.remote.StoryRemoteSource
import com.example.dicodingstoryapp.data.source.remote.responses.ApiResponse
import com.example.dicodingstoryapp.data.source.remote.responses.StoryResponse
import com.example.dicodingstoryapp.data.utils.NetworkBoundResource
import com.example.dicodingstoryapp.domain.model.Story
import com.example.dicodingstoryapp.domain.repository.IStoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val storyRemoteSource: StoryRemoteSource,
    private val storyLocalSource: StoryLocalSource,
    private val storyMapper: StoryMapper,
) : IStoryRepository {
    override fun getAllStory(page: Int, size: Int, isIncludeLocation: Boolean): Flow<Resource<List<Story>>> =
        object : NetworkBoundResource<List<Story>, List<StoryResponse>>() {
            override fun loadFromDB(): Flow<List<Story>> {
                return storyLocalSource.getAllStory().map { stories ->
                    stories.map { storyMapper.fromEntityToDomain(it) }
                }
            }

            override fun shouldFetch(data: List<Story>?) = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<StoryResponse>>> {
                return storyRemoteSource.getAllStory(page, size, isIncludeLocation)
            }

            override suspend fun saveCallResult(data: List<StoryResponse>) {
                val stories = data.map { storyMapper.fromResponseToEntity(it) }
                storyLocalSource.insertStory(stories)
            }
        }.asFlow()

    override fun addNewStory(): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }
}