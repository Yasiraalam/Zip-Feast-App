package com.zip_feast.presentation.products.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.productsModels.AllProductsResponseModel
import com.zip_feast.data.remote.models.productsModels.Data
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
class ProductsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _products = MutableLiveData<Resource<AllProductsResponseModel>>()
    val products: LiveData<Resource<AllProductsResponseModel>> get() = _products

    private val _filteredProducts = MutableStateFlow<Resource<List<Data>>>(Resource.Loading())
    val filteredProducts: StateFlow<Resource<List<Data>>> = _filteredProducts.asStateFlow()

    private val _searchedProduct = MutableStateFlow<Resource<List<Data>>>(Resource.Loading())
    val searchedProduct: StateFlow<Resource<List<Data>>> = _searchedProduct.asStateFlow()

    fun fetchProducts() {
        val token = authRepository.getToken()
        if (token != null) {
            Log.d("ProductsViewModel", "fetchProducts: Token is available, initiating API call.")
            _products.value = Resource.Loading()

            viewModelScope.launch {
                try {
                    val result = userRepository.getProducts(token)
                    _products.value = result
                    // Update the filtered products to the initial list, handling success and error
                    _filteredProducts.value = when (result) {
                        is Resource.Success -> Resource.Success(result.data?.data ?: emptyList())
                        is Resource.Error -> Resource.Error(result.errorMessage.toString())
                        is Resource.Loading -> Resource.Loading()
                    }
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
                    _filteredProducts.value = Resource.Error("An unknown error occurred")

                }
            }
        } else {
            Log.d("products", "Token is null")
        }
    }

    fun filterProducts(query: String) {
        val allProducts = _products.value?.data?.data
        if (allProducts != null) {
            val filtered = allProducts.filter { product ->
                product.name.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true)
            }
            _filteredProducts.value = Resource.Success(filtered)
        } else {
            _filteredProducts.value = Resource.Error("No products available")
        }
    }
    fun resetFilteredProducts() {
        val allProducts = _products.value?.data?.data ?: emptyList()
        _filteredProducts.value = Resource.Success(allProducts)
    }

//    fun searchedProduct(query: String) {
//        val allProducts = _products.value?.data?.data
//        if (allProducts != null) {
//            val queryWords = query.lowercase().split(" ").toSet()
//            val foundProducts = allProducts.filter { product ->
//                queryWords.any { word -> product.name.lowercase() == word } ||
//                        product.category.lowercase().contains(query.lowercase())
//            }
//            if (foundProducts.isNotEmpty()) {
//                _searchedProduct.value = Resource.Success(foundProducts)
//            } else {
//                _searchedProduct.value = Resource.Error("Products Not found")
//            }
//        } else {
//            _searchedProduct.value = Resource.Error("Products Not found")
//        }
//    }
//    fun resetSearchedProducts() {
//        val searchedProduct = _searchedProduct.value?.data?: emptyList()
//        _filteredProducts.value = Resource.Success(searchedProduct)
//    }
}

