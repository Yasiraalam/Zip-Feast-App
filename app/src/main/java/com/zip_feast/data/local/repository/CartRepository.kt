package com.zip_feast.data.local.repository

import com.zip_feast.data.local.models.CartItem
import com.zip_feast.data.local.roomDb.CartItemDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CartRepository @Inject constructor(private val cartItemDao: CartItemDao) {

    val allCartItems: Flow<List<CartItem>> = cartItemDao.getAllCartItems()

    suspend fun insert(cartItem: CartItem) {
        cartItemDao.insertCartItem(cartItem)
    }

    suspend fun delete(cartItem: CartItem) {
        cartItemDao.deleteCartItems(cartItem)
    }

    suspend fun deleteById(productId: Int) {
        cartItemDao.deleteCartItemById(productId)
    }
}
