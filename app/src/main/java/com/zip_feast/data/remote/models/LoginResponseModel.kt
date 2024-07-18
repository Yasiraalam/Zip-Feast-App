package com.zip_feast.data.remote.models

data class LoginResponseModel(
    val accessToken: String,
    val message: String,
    val refreshToken: String,
    val status: String,
    val user: User
)