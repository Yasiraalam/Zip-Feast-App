package com.zip_feast.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = false)
    val productId: Int,
    val imageResId: Int,
    val name: String,
    val price: Double,
    val discount: String,
    val rating: Int,
    var quantity: Int,
)
