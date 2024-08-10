package com.zip_feast.presentation.dashboard.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.ordersModels.ordersResponse.CartOrderResponseModel
import com.zip_feast.data.remote.models.serviceProviders.AllServiceProvidersResponseModel
import com.zip_feast.data.remote.models.serviceProviders.ServiceProviderDetailResponse
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.data.remote.repository.ServiceProvidersRepository
import com.zip_feast.utils.apputils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceProvidersViewModel @Inject constructor(
    private val serviceProvidersRepository: ServiceProvidersRepository,
    private val authRepository: AuthRepository
) :ViewModel() {

    private val _serviceProviders =MutableStateFlow<Resource<AllServiceProvidersResponseModel>>(Resource.Loading())
    val serviceProviders: StateFlow<Resource<AllServiceProvidersResponseModel>> =_serviceProviders.asStateFlow()

    private val _serviceProviderDetail =MutableStateFlow<Resource<ServiceProviderDetailResponse>>(Resource.Loading())
    val serviceProviderDetail: StateFlow<Resource<ServiceProviderDetailResponse>> =_serviceProviderDetail.asStateFlow()

    fun getAllServiceProviders(){
        val token = authRepository.getToken()
        if(token != null){
            viewModelScope.launch {
                try {
                    val result = serviceProvidersRepository.getAllServiceProviders(token=token)
                    Log.d("Yas", "ServiceProviders: ${result.data?.data}")
                    _serviceProviders.value = result
                } catch (e: Exception) {
                    Log.d("ServiceProviders", "ServiceProviders: provider Errors")
                    _serviceProviders.value =
                        Resource.Error("An unknown error occurred. Try again!")
                }
            }
        }else{
            Log.d("ServiceProviders" ,"fetchUserOrders: Token is null")
        }
    }
}