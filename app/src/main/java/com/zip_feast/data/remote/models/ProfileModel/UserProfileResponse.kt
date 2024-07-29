package com.zip_feast.data.remote.models.ProfileModel

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    val `data`: UserProfileData,
    val message: String,
    val status: String
)