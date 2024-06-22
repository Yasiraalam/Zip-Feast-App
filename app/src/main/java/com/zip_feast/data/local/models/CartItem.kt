package com.zip_feast.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val productId: Int,
    val title: String,
    val price: Int,
    var quantity: Int
)
