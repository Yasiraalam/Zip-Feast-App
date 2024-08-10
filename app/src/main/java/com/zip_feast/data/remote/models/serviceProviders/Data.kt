package com.zip_feast.data.remote.models.serviceProviders

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val address: String,
    val avatar: String,
    val city: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val pincode: String,
    val price: String,
    val serviceType: String,
    val state: String
)
