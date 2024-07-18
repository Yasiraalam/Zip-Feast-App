package com.zip_feast.presentation.auth.authviewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.UserRequest
import com.zip_feast.data.remote.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            try {
                val response = userRepository.registerUser(userRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("authviewmodel", "registerUser: Registration successful, message: ${it.message}")
                    } ?: run {
                        Log.d("authviewmodel", "registerUser: Unexpected error, empty response body")
                    }
                } else {
                    Log.d("authviewmodel", "registerUser: Registration failed, response message: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("authviewmodel", "registerUser: Exception occurred - ${e.message}")
            }
        }
    }
}
