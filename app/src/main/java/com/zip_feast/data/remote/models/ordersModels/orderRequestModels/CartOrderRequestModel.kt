package com.zip_feast.data.remote.models.ordersModels.orderRequestModels

import kotlinx.serialization.Serializable

@Serializable
data class CartOrderRequestModel(
    val `cart`: List<UserOrderModel>,
    val deliveryAddress: String,
    val paymentMethod: String
)