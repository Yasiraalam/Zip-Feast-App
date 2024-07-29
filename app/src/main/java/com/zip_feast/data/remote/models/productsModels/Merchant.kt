package com.zip_feast.data.remote.models.productsModels

import kotlinx.serialization.Serializable


@Serializable
data class Merchant(
    val address: String?,
    val email: String?,
    val name: String?,
    val storeName: String
)