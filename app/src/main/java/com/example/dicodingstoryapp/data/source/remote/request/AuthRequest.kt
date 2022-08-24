package com.example.dicodingstoryapp.data.source.remote.request

import com.squareup.moshi.Json

data class AuthRequest(
    @Json(name = "email")
    val email: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "name")
    val name: String? = null,
)
