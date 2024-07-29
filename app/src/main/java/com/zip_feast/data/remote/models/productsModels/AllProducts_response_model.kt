package com.zip_feast.data.remote.models.productsModels

data class AllProductsResponseModel(
    val data: List<Data>,
    val meta: Meta,
    val status: String
)