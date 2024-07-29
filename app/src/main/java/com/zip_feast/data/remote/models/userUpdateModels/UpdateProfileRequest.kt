package com.zip_feast.data.remote.models.userUpdateModels

import com.zip_feast.data.remote.models.ProfileModel.Order
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @Contextual val address: String? = null,
    @Contextual val avatar: String? = null,
    @Contextual val city: String? = null,
    val email: String,
    val name: String,
    @Contextual val phone: String? = null,
    @Contextual val pincode: String? = null,
    @Contextual val state: String? = null
)

data class UserInfoUpdate(
    val email: String,
    val phone: String? = null,
    val address: String? = null,

)
