package com.example.dicodingstoryapp.data.source.remote.responses

import com.squareup.moshi.Json

data class LoginResponse(

	@Json(name="loginResult")
	val userInfo: UserInfoResponse,

	@Json(name="error")
	val error: Boolean,

	@Json(name="message")
	val message: String
)

data class UserInfoResponse(

	@Json(name="name")
	val name: String,

	@Json(name="userId")
	val userId: String,

	@Json(name="token")
	val token: String
)
