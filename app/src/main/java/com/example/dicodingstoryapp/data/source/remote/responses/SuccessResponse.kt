package com.example.dicodingstoryapp.data.source.remote.responses

import com.squareup.moshi.Json

data class SuccessResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)
