package com.zip_feast.data.remote.models.ordersModels

import kotlinx.serialization.Serializable

@Serializable
data class CartOrderResponseModel(
    val `data`: List<Data>,
    val message: String,
    val status: String
)