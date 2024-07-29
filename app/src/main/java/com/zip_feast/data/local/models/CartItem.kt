package com.zip_feast.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zip_feast.data.remote.models.productsModels.Merchant

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category: String,
    val createdAt: String,
    val description: String,
    val isAvailable: Boolean,
    val merchant: Merchant,
    val merchantId: Int,
    val name: String,
    val price: String,
    val productImage: String,
    val stock: String,
    val updatedAt: String,
    val quantity: Int
)
