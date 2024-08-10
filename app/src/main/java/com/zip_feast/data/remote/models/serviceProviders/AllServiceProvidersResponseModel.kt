package com.zip_feast.data.remote.models.serviceProviders

import kotlinx.serialization.Serializable


@Serializable
data class AllServiceProvidersResponseModel(
    val `data`: List<Data>,
    val status: String
)