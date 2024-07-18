package com.zip_feast.data.remote.apiService

import com.zip_feast.data.remote.models.LoginModel
import com.zip_feast.data.remote.models.LoginResponseModel
import com.zip_feast.data.remote.models.UserRequest
import com.zip_feast.data.remote.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("user/register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("user/login")
    suspend fun LoginUser(@Body loginModel: LoginModel): Response<LoginResponseModel>
}