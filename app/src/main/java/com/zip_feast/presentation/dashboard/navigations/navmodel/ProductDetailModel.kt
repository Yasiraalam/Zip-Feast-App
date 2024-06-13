package com.zip_feast.presentation.dashboard.navigations.navmodel

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetail(
    val imageResId: Int,
    val name: String,
    val price: String,
    val discount: String,
    val rating: Int
)
