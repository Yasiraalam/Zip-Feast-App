package com.zip_feast.data.remote.models.ordersModels.ordersResponse

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val orderId: Int,
    val product: Product,
    val productId: Int,
    val quantity: Int
)