package com.zip_feast.data.remote.models.updatemodels

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateResModel(
    val `data`: UserUpdatedModel,
    val message: String,
    val status: String
)