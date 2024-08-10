package com.zip_feast.data.remote.models.ordersModels.ordersResponse

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val createdAt: String,
    val deliveryAddress: String,
    val id: Int,
    val items: List<Item>,
    val merchantId: Int,
    val orderStatus: String,
    val paymentMethod: String,
    val totalAmount: Int,
    val totalQuantity: Int,
    val updatedAt: String,
    val userId: Int
)