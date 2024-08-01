package com.zip_feast.data.remote.models.ordersModels

import kotlinx.serialization.Serializable

@Serializable
data class UserOrderRequestModel(
    val userOrder: List<UserOrderModel>,
    val deliveryAddress: String,
    val paymentMethod: String
)