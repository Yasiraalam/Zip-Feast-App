package com.zip_feast.presentation.orders.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.ordersModels.CartOrderRequestModel
import com.zip_feast.data.remote.models.ordersModels.CartOrderResponseModel
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.data.remote.repository.UserRepository
import com.zip_feast.utils.apputils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _orderInformation =
        MutableStateFlow<Resource<CartOrderResponseModel>>(Resource.Loading())
    val orderInformation: StateFlow<Resource<CartOrderResponseModel>> =
        _orderInformation.asStateFlow()

    private val _fetchUserOrders= MutableStateFlow<Resource<CartOrderResponseModel>>(Resource.Loading())
    val fetchUserOrders: StateFlow<Resource<CartOrderResponseModel>> =_fetchUserOrders.asStateFlow()

    fun placeOrder(cartOrderRequestModel: CartOrderRequestModel) {
        val token = authRepository.getToken()
        if (token != null) {
            viewModelScope.launch {
                _orderInformation.value = Resource.Loading()
                viewModelScope.launch {
                    try {
                        val result = userRepository.userOrder(token, cartOrderRequestModel)
                        Log.d("PlaceOrder", "placeOrder: ${result.data}")
                        _orderInformation.value = result
                    } catch (e: Exception) {
                        Log.d("PlaceOrder", "placeOrder: error Order")
                        _orderInformation.value =
                            Resource.Error("An unknown error occurred. Try again!")
                    }
                }
            }
        } else {
            Log.d("PlaceOrder", "placeOrder: token is null")
        }
    }
    fun fetchUserOrders(cartOrderRequestModel: CartOrderRequestModel){
        val token = authRepository.getToken()
        if(token != null){
            viewModelScope.launch {
                try {
                    val result = userRepository.fetchUserOrders(token, cartOrderRequestModel)
                    Log.d("FetchOrder", "fetchUserOrders: ${result.data?.data}")
                    _fetchUserOrders.value = result
                } catch (e: Exception) {
                    Log.d("FetchOrder", "placeOrder: error Order")
                    _fetchUserOrders.value =
                        Resource.Error("An unknown error occurred. Try again!")
                }
            }
        }else{
            Log.d("FetchOrder" ,"fetchUserOrders: Token is null")
        }
    }
}