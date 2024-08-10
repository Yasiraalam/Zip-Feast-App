package com.zip_feast.data.remote.models.ordersModels.ordersResponse

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val category: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val isAvailable: Boolean,
    val merchantId: Int,
    val name: String,
    val price: String,
    val productImage: String,
    val stock: String,
    val updatedAt: String
)