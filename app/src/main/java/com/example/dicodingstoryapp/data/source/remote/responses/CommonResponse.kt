package com.example.dicodingstoryapp.data.source.remote.responses

import com.squareup.moshi.Json

data class CommonResponse(

	@Json(name="error")
	val error: Boolean,

	@Json(name="message")
	val message: String
)
