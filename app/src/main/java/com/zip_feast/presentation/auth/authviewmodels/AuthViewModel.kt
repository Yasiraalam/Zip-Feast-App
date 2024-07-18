package com.zip_feast.presentation.auth.authviewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message
    fun clearMessage() {
        _message.value = ""
    }
    fun registerUser(userRequest: UserRequest,onLoginSuccess:()->Unit){
        viewModelScope.launch {
            try {
                val response = userRepository.registerUser(userRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _message.value = it.status
                        onLoginSuccess()

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
}
