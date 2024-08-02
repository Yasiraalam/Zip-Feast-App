package com.zip_feast.data.remote.models.ordersModels

import kotlinx.serialization.Serializable

@Serializable
data class UserOrderModel(
    val productId:Int,
    val quantity:Int
)
