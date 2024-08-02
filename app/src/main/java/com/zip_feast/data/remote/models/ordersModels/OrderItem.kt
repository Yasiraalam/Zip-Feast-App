package com.zip_feast.data.remote.models.ordersModels

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val orderStatus : String,
    val paymentMethod: String,
    val totalAmount: Int,
    val totalQuantity: Int,
    val deliveryAddress: String
)
