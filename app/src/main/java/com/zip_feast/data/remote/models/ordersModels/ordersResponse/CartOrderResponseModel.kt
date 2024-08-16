package com.zip_feast.data.remote.models.ordersModels.ordersResponse

import kotlinx.serialization.Serializable

@Serializable
data class CartOrderResponseModel(
    val `data`: List<OrderDetails>,
    val message: String,
    val status: String
)