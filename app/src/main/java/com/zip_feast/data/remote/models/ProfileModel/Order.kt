package com.zip_feast.data.remote.models.ProfileModel

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val createdAt: String,
    val deliveryAddress: String,
    val id: Int,
    val merchantId: Int,
    val orderStatus: String,
    val paymentMethod: String,
    val totalAmount: Int,
    val totalQuantity: Int,
    val updatedAt: String,
    val userId: Int
)