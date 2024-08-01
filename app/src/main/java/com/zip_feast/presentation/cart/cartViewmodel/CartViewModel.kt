package com.zip_feast.presentation.cart.cartViewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.local.models.CartItem
import com.zip_feast.data.local.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {

    val allCartItems = repository.allCartItems.asLiveData()

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> get() = _totalQuantity

    private val _totalPrice = MutableLiveData(0.0)
    val totalPrice: LiveData<Double> get() = _totalPrice

    init {
        // Observe changes in the cart items to recalculate totals
        allCartItems.observeForever { items ->
            calculateTotals(items)
        }
    }

    fun insert(cartItem: CartItem) = viewModelScope.launch {
        repository.insert(cartItem)
    }

    fun delete(cartItem: CartItem) = viewModelScope.launch {
        repository.delete(cartItem)
    }

    fun deleteById(productId: Int) = viewModelScope.launch {
        repository.deleteById(productId)
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            if (cartItem.quantity < cartItem.stock.toInt()) {
                val newQuantity = cartItem.quantity + 1
                val updatedCartItem = cartItem.copy(quantity = newQuantity)
                repository.updateCartItem(updatedCartItem)
            }
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            viewModelScope.launch {
                val newQuantity = cartItem.quantity - 1
                val updatedCartItem = cartItem.copy(quantity = newQuantity)
                repository.updateCartItem(updatedCartItem)
            }
        }
    }

    private fun calculateTotals(cartItems: List<CartItem>) {
        var totalQty = 0
        var totalPrc = 0.0

        for (item in cartItems) {
            totalQty += item.quantity
            totalPrc += item.quantity * item.price.toDouble() +70.00
        }
        _totalQuantity.value = totalQty
        _totalPrice.value = totalPrc
    }

}
