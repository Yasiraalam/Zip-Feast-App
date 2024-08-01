package com.zip_feast.data.remote.repository

import android.util.Log
import com.zip_feast.data.remote.apiService.UserApi
import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.data.remote.models.productsModels.AllProductsResponseModel
import com.zip_feast.data.remote.models.loginModel.LoginModel
import com.zip_feast.data.remote.models.loginModel.LoginResponseModel
import com.zip_feast.data.remote.models.ProfileModel.UserProfileResponse
import com.zip_feast.data.remote.models.loginModel.UserRequest
import com.zip_feast.data.remote.models.loginModel.UserResponse
import com.zip_feast.data.remote.models.ordersModels.UserOrderRequestModel
import com.zip_feast.data.remote.models.userUpdateModels.UserInfoUpdate
import com.zip_feast.utils.apputils.Resource
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserApi) {

    suspend fun registerUser(userRequest: UserRequest): Response<UserResponse> {
        return userApi.registerUser(userRequest)
    }

    suspend fun loginUser(loginModel: LoginModel): Response<LoginResponseModel> {
        return  userApi.loginUser(loginModel)
    }

    suspend fun getProducts(token: String): Resource<AllProductsResponseModel> {
        return try {
            val response = userApi.getAllProducts("Bearer $token")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Resource.Error("Response body is null")
                }
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error( "An unknown error occurred.Try again!")
        }
    }
    suspend fun getUserProfile(token: String): Resource<UserProfileResponse> {
        return try {
            val response = userApi.getProfileInfo("Bearer $token")
            Log.d("repository", "${response.body() } ")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Resource.Error("Response body is null")
                }
            } else {
                Resource.Error("UserProfile Not Updated")
            }
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred. Try again!")
        }
    }

    suspend fun updateUserProfile(token: String, userInfoUpdate: UserInfoUpdate): Resource<UserProfileResponse> {
        return try {
            val response = userApi.updateUserProfile("Bearer $token", userInfoUpdate)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Repository", "updateUserProfile: ${body.data.phone}")
                    Resource.Success(body)
                } else {
                    Resource.Error("Response body is null")
                }
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred. Try again!")
        }
    }
    suspend fun updateUserAddress(token: String, userAddress: UserAddress): Resource<UserProfileResponse> {
        return try {
            val response = userApi.updateUserAddress("Bearer $token", userAddress)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Repository", "updateUserProfile: ${body}")
                    Resource.Success(body)
                } else {
                    Resource.Error("Response body is null")
                }
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred. Try again!")
        }
    }
    suspend fun userOrder(token: String, userOrderRequestModel: UserOrderRequestModel): Resource<UserOrderRequestModel> {
        return try {
            val response = userApi.userOrder("Bearer $token", userOrderRequestModel)
            Log.d("Repository", "Response code: ${response.code()}")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Repository", "userOrder response body: $body")
                    Resource.Success(body)
                } else {
                    Log.d("Repository", "Response body is null")
                    Resource.Error("Response body is null")
                }
            } else {
                Log.d("Repository", "Error response: ${response.message()}")
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("Repository", "Exception: ${e.message}")
            Resource.Error("An unknown error occurred. Try again!")
        }
    }

}
