package com.zip_feast.presentation.dashboard.navigations.navmodel

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetail(
    val productId: Int,
    val imageResId: Int,
    val name: String,
    val price: Double,
    val discount: String,
    val rating: Int,
    var quantity: Int,
)
