package com.example.dicodingstoryapp.data.source.remote.responses

import com.squareup.moshi.Json

data class ListStoryResponse(

	@Json(name="listStory")
	val listStory: List<StoryResponse>? = null,

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)