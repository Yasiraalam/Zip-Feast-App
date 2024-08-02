package com.zip_feast.data.remote.models.ProfileModel

import kotlinx.serialization.Serializable

@Serializable
data class UserAddress(
    val state:String?=null,
    val address:String?=null,
    val city:String?=null,
    val phone:String?=null
)
