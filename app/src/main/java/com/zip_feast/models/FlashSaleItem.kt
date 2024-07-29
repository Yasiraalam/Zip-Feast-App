package com.zip_feast.models

import com.zip_feast.data.remote.models.productsModels.Merchant


data class FlashSaleItem(
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

