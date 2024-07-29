package com.zip_feast.data.remote.models.productsModels

import kotlinx.serialization.Serializable


@Serializable
data class Data(
    val category: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val isAvailable: Boolean,
    val merchant: Merchant,
    val merchantId: Int,
    val name: String,
    val price: String,
    val productImage: String,
    val stock: String,
    val updatedAt: String
)