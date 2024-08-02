package com.zip_feast.data.remote.models.ordersModels

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int
)