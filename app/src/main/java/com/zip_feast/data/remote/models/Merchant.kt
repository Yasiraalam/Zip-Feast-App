package com.zip_feast.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data class Merchant(
    val address: String?,
    val email: String?,
    val name: String?,
    val storeName: String
)