package com.zip_feast.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zip_feast.data.local.models.CartItem

object CartSharedPreferences {
    private const val PREFS_NAME = "cart_prefs"
    private const val KEY_CART_ITEMS = "cart_items"

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveCartItem(cartItem: CartItem) {
        val cartItems = getCartItems().toMutableList()
        val existingItem = cartItems.find { it.productId == cartItem.productId }
        if (existingItem != null) {
            existingItem.quantity += cartItem.quantity
        } else {
            cartItems.add(cartItem)
        }
        sharedPreferences.edit().putString(KEY_CART_ITEMS, gson.toJson(cartItems)).apply()
    }

    private fun getCartItems(): List<CartItem> {
        val json = sharedPreferences.getString(KEY_CART_ITEMS, "")
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun clearCart() {
        sharedPreferences.edit().remove(KEY_CART_ITEMS).apply()
    }
}
