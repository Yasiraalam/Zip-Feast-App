package com.zip_feast.presentation.dashboard.navigations.navmodel

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetail(
    val productId: Int,
    val name: String,
    val price: Int,
    val discount: String,
    val rating: Int
)
