package com.zip_feast.data.local.models

data class CartItem(
    val productId: Int,
    val title: String,
    val price: Double,
    var quantity: Int
)
