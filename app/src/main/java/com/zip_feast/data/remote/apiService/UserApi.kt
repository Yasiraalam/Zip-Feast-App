package com.zip_feast.data.remote.apiService

import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.data.remote.models.productsModels.AllProductsResponseModel
import com.zip_feast.data.remote.models.loginModel.LoginModel
import com.zip_feast.data.remote.models.loginModel.LoginResponseModel
import com.zip_feast.data.remote.models.ProfileModel.UserProfileResponse
import com.zip_feast.data.remote.models.loginModel.UserRequest
import com.zip_feast.data.remote.models.loginModel.UserResponse
import com.zip_feast.data.remote.models.ordersModels.UserOrderRequestModel
import com.zip_feast.data.remote.models.userUpdateModels.UserInfoUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {
    @POST("user/register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("user/login")
    suspend fun loginUser(@Body loginModel: LoginModel): Response<LoginResponseModel>

    @GET("products")
    suspend fun getAllProducts(
        @Header("Authorization") token: String
    ): Response<AllProductsResponseModel>

    @GET("user/profile")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String
    ):Response<UserProfileResponse>

    @PUT("profile")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userInfoUpdate: UserInfoUpdate
    ): Response<UserProfileResponse>

    @PUT("profile")
    suspend fun updateUserAddress(
        @Header("Authorization") token: String,
        @Body userAddress: UserAddress
    ): Response<UserProfileResponse>

    @POST("user/order/create")
    suspend fun userOrder(
        @Header("Authorization") token: String,
        @Body userOrderRequestModel: UserOrderRequestModel
    ):Response<UserOrderRequestModel>


}