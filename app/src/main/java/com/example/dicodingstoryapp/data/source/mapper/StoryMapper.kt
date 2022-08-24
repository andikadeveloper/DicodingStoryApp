package com.example.dicodingstoryapp.data.source.mapper

import com.example.dicodingstoryapp.data.source.local.entity.StoryEntity
import com.example.dicodingstoryapp.data.source.remote.responses.StoryResponse
import com.example.dicodingstoryapp.domain.model.Story
import javax.inject.Singleton

class StoryMapper {
    fun fromEntityToDomain(from: StoryEntity) = Story(
        id = from.id,
        name = from.name,
        description = from.description,
        photoUrl = from.photoUrl,
        createdAt = from.createdAt,
    )

    fun fromResponseToEntity(from: StoryResponse) = StoryEntity(
        id = from.id ?: "",
        name = from.name ?: "",
        description = from.description ?: "",
        photoUrl = from.photoUrl ?: "",
        createdAt = from.createdAt ?: "",
    )
}