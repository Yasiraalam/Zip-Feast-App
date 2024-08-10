package com.zip_feast.data.remote.models.ordersModels.orderRequestModels

import kotlinx.serialization.Serializable

@Serializable
data class UserOrderModel(
    val productId:Int,
    val quantity:Int
)
