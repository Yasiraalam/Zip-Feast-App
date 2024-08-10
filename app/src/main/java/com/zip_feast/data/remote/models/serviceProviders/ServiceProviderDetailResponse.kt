package com.zip_feast.data.remote.models.serviceProviders

import kotlinx.serialization.Serializable

@Serializable
data class ServiceProviderDetailResponse(
    val `data`: Data?,
    val status: String
)