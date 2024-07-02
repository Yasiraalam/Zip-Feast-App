package com.zip_feast.models


data class FlashSaleItem(
    val productId:Int,
    val imageResId: Int,
    val name: String,
    val price: Double,
    val discount: String,
    val rating:Int
)
