package com.zip_feast.data.remote.apiService

import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.data.remote.models.productsModels.AllProductsResponseModel
import com.zip_feast.data.remote.models.loginModel.LoginModel
import com.zip_feast.data.remote.models.loginModel.LoginResponseModel
import com.zip_feast.data.remote.models.ProfileModel.UserProfileResponse
import com.zip_feast.data.remote.models.loginModel.UserRequest
import com.zip_feast.data.remote.models.loginModel.UserResponse
import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.CartOrderRequestModel
import com.zip_feast.data.remote.models.ordersModels.ordersResponse.CartOrderResponseModel
import com.zip_feast.data.remote.models.serviceProviders.AllServiceProvidersResponseModel
import com.zip_feast.data.remote.models.serviceProviders.ServiceProviderDetailResponse
import com.zip_feast.data.remote.models.userUpdateModels.UserUpdateResModel
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
    ): Response<UserProfileResponse>

    @PUT("user/profile/update")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userInfoUpdate: UserInfoUpdate
    ): Response<UserUpdateResModel>

    @PUT("profile")
    suspend fun updateUserAddress(
        @Header("Authorization") token: String,
        @Body userAddress: UserAddress
    ): Response<UserProfileResponse>

    @POST("user/order/create")
    suspend fun userOrder(
        @Header("Authorization") token: String,
        @Body cartOrderRequestModel: CartOrderRequestModel
    ): Response<CartOrderResponseModel>

    @GET("user/order/all")
    suspend fun getAllUserOrders(
        @Header("Authorization") token: String
    ):Response<CartOrderResponseModel>

    @GET("service-providers")
    suspend fun getAllServiceProviders(
        @Header("Authorization") token: String
    ):Response<AllServiceProvidersResponseModel>

    @GET("service-providers")
    suspend fun serviceProvidersDetail(
        @Header("serviceId") serviceId :Int,
        @Header("Authorization") token: String
    ):Response<ServiceProviderDetailResponse>

//    @GET("user/order/{orderId}")
//    suspend fun getOrderDetails(
//        @Header("OrderId") orderId :Int,
//        @Header("Authorization") token: String
//    ):Response<OrderDetailsResponseModel>


}