package com.zip_feast.data.remote.repository

import android.util.Log
import com.zip_feast.data.remote.apiService.UserApi
import com.zip_feast.data.remote.models.serviceProviders.AllServiceProvidersResponseModel
import com.zip_feast.data.remote.models.serviceProviders.ServiceProviderDetailResponse
import com.zip_feast.presentation.dashboard.viewmodels.ServiceProvidersViewModel
import com.zip_feast.utils.apputils.Resource
import javax.inject.Inject

class ServiceProvidersRepository@Inject constructor(private val userApi: UserApi) {

    suspend fun getAllServiceProviders(token: String): Resource<AllServiceProvidersResponseModel> {
        return try {
            val response = userApi.getAllServiceProviders("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ServiceProviders", "ServiceProviders response body: ${it.data.first()}")
                    Resource.Success(it)
                } ?: run {
                    Log.d("ServiceProviders", "Response body is null")
                    Resource.Error("Response body is null")
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Log.d("ServiceProviders", "Error response: $errorMsg")
                Resource.Error("Error: $errorMsg")
            }
        } catch (e: Exception) {
            Log.e("ServiceProviders", "Exception: ${e.message}")
            Resource.Error("Exception: ${e.message}")
        }
    }

}