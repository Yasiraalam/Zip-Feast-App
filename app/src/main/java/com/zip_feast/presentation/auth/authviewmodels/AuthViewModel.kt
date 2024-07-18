package com.zip_feast.presentation.auth.authviewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.LoginModel
import com.zip_feast.data.remote.models.LoginResponseModel
import com.zip_feast.data.remote.models.UserRequest
import com.zip_feast.data.remote.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun clearMessage() {
        _message.value = ""
    }
    fun registerUser(userRequest: UserRequest, onRegisterSuccess:()->Unit){
        viewModelScope.launch {
            try {
                val response = userRepository.registerUser(userRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _message.value = it.status
                        onRegisterSuccess()
                        Log.d("authviewmodel", "registerUser: Registration successful")
                    } ?: run {
                        _message.value = "Unexpected error, empty response body"
                    }
                } else {
                    _message.value = "failed"
                    Log.d("authviewmodel", "registerUser: Registration failed, response message: ${response.message()}")
                }
            } catch (e: Exception) {
                _message.value = "Exception occurred: ${e.message}"
                Log.d("authviewmodel", "registerUser: Exception occurred - ${e.message}")
            }
        }
    }

    fun loginUser(loginModel: LoginModel, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(loginModel)
                handleResponse(response, onLoginSuccess)
            } catch (e: Exception) {
                _message.value = "Exception occurred: ${e.message}"
                Log.e("AuthViewModel", "loginUser: Exception occurred - ${e.message}", e)
            }
        }
    }
    private fun handleResponse(response: Response<LoginResponseModel>, onLoginSuccess: () -> Unit) {
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                _message.value = it.status
                saveToken(it.refreshToken)
                onLoginSuccess()
                Log.d("AuthViewModel", "loginUser: Login successful")
            } ?: run {
                _message.value = "Unexpected error: empty response body"
                Log.w("AuthViewModel", "loginUser: Unexpected error, empty response body")
            }
        } else {
            _message.value = response.body()?.message ?: "Login failed: ${response.message()}"
            Log.w("AuthViewModel", "loginUser: Login failed - ${response.message()}")
        }
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }
}
