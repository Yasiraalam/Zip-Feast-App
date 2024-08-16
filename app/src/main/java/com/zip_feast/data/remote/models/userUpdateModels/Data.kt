package com.zip_feast.data.remote.models.userUpdateModels

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdatedModel(
    val address: String,
    val city: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val pincode: String,
    val state: String
)