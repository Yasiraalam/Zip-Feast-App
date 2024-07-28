package com.zip_feast.data.remote.repository

import android.util.Log
import com.zip_feast.data.remote.apiService.UserApi
import com.zip_feast.data.remote.models.AllProductsResponseModel
import com.zip_feast.data.remote.models.LoginModel
import com.zip_feast.data.remote.models.LoginResponseModel
import com.zip_feast.data.remote.models.ProfileModel.UserProfileResponse
import com.zip_feast.data.remote.models.UserRequest
import com.zip_feast.data.remote.models.UserResponse
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
}
