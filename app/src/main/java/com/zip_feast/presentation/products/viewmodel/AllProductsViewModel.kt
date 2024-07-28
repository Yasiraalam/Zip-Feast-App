package com.zip_feast.presentation.products.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.AllProductsResponseModel
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.data.remote.repository.UserRepository
import com.zip_feast.utils.apputils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _products = MutableLiveData<Resource<AllProductsResponseModel>>()
    val products: LiveData<Resource<AllProductsResponseModel>> get() = _products
//    init {
//        fetchProducts()
//    }
    fun fetchProducts() {
        val token = authRepository.getToken()
        if (token != null) {
            Log.d("ProductsViewModel", "fetchProducts: Token is available, initiating API call.")
            _products.value = Resource.Loading()

            viewModelScope.launch {
                try {
                    val result = userRepository.getProducts(token)
                    _products.value = result
                    when (result) {
                        is Resource.Success -> {
                            Log.d(
                                "ProductsViewModel",
                                "fetchProducts: Products fetched successfully: ${result.data}"
                            )
                        }

                        is Resource.Error -> {
                            Log.e(
                                "ProductsViewModel",
                                "fetchProducts: Error fetching products: ${result.errorMessage}"
                            )
                        }
                        is Resource.Loading -> TODO()
                    }
                } catch (e: Exception) {
                    _products.value = Resource.Error("An unknown error occurred")
                    Log.e("ProductsViewModel", "fetchProducts: Exception: ${e.message}", e)
                }
            }
        } else {
            Log.d("products", "Token is null")
        }
    }
}

