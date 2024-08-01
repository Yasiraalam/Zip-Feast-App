package com.zip_feast.presentation.orders.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.ordersModels.UserOrderRequestModel
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
class PlaceOrderViewModel@Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
):ViewModel() {

    private val _orderInformation = MutableStateFlow<Resource<UserOrderRequestModel>>(Resource.Loading())
    val orderInformation: StateFlow<Resource<UserOrderRequestModel>> = _orderInformation.asStateFlow()

    fun placeOrder(userOrderRequestModel: UserOrderRequestModel) {
        val token = authRepository.getToken()
        viewModelScope.launch {
            if (token != null) {
                _orderInformation.value = Resource.Loading()
                viewModelScope.launch {
                    _orderInformation.value = Resource.Loading()
                    try {
                        val result = userRepository.userOrder(token, userOrderRequestModel)
                        Log.d("PlaceOrder", "placeOrder: ${result.data}")
                        _orderInformation.value = result
                    } catch (e: Exception) {
                        Log.d("PlaceOrder", "placeOrder: error Order")
                        _orderInformation.value =
                            Resource.Error("An unknown error occurred. Try again!")
                    }
                }
            }
        }

    }

}