package com.zip_feast.data.remote.models.ProfileModel

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileData(
    val Favorite: List<String>,
    @Contextual val address: String? = null,
    @Contextual val avatar: String? = null,
    @Contextual val city: String? = null,
    val email: String,
    val id: Int,
    val name: String,
    val orders: List<Order>,
    @Contextual val phone: String? = null,
    @Contextual val pincode: String? = null,
    @Contextual val state: String? = null
)