package com.zip_feast.data.remote.repository

import com.zip_feast.data.remote.apiService.UserApi
import com.zip_feast.data.remote.models.UserRequest
import com.zip_feast.data.remote.models.UserResponse
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserApi) {

    suspend fun registerUser(userRequest: UserRequest): Response<UserResponse> {
        return userApi.registerUser(userRequest)
    }
}
