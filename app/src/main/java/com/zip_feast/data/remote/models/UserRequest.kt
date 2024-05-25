package com.zip_feast.data.remote.models

data class UserRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val role:String?=null
)
