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
import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.CartOrderRequestModel
import com.zip_feast.data.remote.models.ordersModels.ordersResponse.CartOrderResponseModel
import com.zip_feast.data.remote.models.userUpdateModels.UserUpdateResModel
import com.zip_feast.data.remote.models.userUpdateModels.UserInfoUpdate
import com.zip_feast.utils.apputils.Resource
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserApi) {

    suspend fun registerUser(userRequest: UserRequest): Response<UserResponse> {
        return userApi.registerUser(userRequest)
    }

    suspend fun loginUser(loginModel: LoginModel): Response<LoginResponseModel> {
        return userApi.loginUser(loginModel)
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
            Resource.Error("An unknown error occurred.Try again!")
        }
    }

    suspend fun getUserProfile(token: String): Resource<UserProfileResponse> {
        return try {
            val response = userApi.getProfileInfo("Bearer $token")
            Log.d("repository", "${response.body()} ")
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

    suspend fun updateUserProfile(
        token: String,
        userInfoUpdate: UserInfoUpdate
    ): Resource<UserUpdateResModel> {
        return try {
            val response = userApi.updateUserProfile("Bearer $token", userInfoUpdate)
            Log.d("updateUserProfileRepository", "updateUserProfile: before success")
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("updateUserProfileRepository", "updateUserProfile: ${body?.data?.phone}")
                if (body != null) {
                    Log.d("updateUserProfileRepository", "updateUserProfile: ${body.data.phone}")
                    Resource.Success(body)
                } else {
                    Resource.Error("Response body or data is null")
                }
            } else {
                Log.d("updateUserProfileRepository", "Error occured ${response.message()}")
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred. Try again!")
        }
    }

    suspend fun updateUserAddress(
        token: String,
        userAddress: UserAddress
    ): Resource<UserProfileResponse> {
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

    suspend fun userOrder(
        token: String,
        cartOrderRequestModel: CartOrderRequestModel
    ): Resource<CartOrderResponseModel> {
        return try {
            val response = userApi.userOrder("Bearer $token", cartOrderRequestModel)
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("RepositoryOrder", "userOrder response body: $it")
                    Resource.Success(it)
                } ?: run {
                    Log.d("RepositoryOrder", "Response body is null")
                    Resource.Error("Response body is null")
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Log.d("RepositoryOrder", "Error response: $errorMsg")
                Resource.Error("Error: $errorMsg")
            }
        } catch (e: Exception) {
            Log.d("RepositoryOrder", "Exception: ${e.message}")
            Resource.Error("An unknown error occurred. Try again!")
        }
    }

    suspend fun fetchUserOrders(token: String): Resource<CartOrderResponseModel> {
        return try {
            val response = userApi.getAllUserOrders("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("RepositoryOrder", "userOrder response body: ${it.data.first()}")
                    Resource.Success(it)
                } ?: run {
                    Log.d("RepositoryOrder", "Response body is null")
                    Resource.Error("Response body is null")
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Log.d("RepositoryOrder", "Error response: $errorMsg")
                Resource.Error("Error: $errorMsg")
            }
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred. Try again!")
        }
    }

}
