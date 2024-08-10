package com.zip_feast.presentation.auth.authviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.loginModel.LoginModel
import com.zip_feast.data.remote.models.loginModel.LoginResponseModel
import com.zip_feast.data.remote.models.loginModel.UserRequest
import com.zip_feast.data.remote.models.loginModel.UserResponse
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.data.remote.repository.UserRepository
import com.zip_feast.utils.apputils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpState = MutableLiveData<Resource<UserResponse>>()
    val signUpState: LiveData<Resource<UserResponse>> get() = _signUpState


    private val _loginState = MutableLiveData<Resource<LoginResponseModel>>()
    val loginState: LiveData<Resource<LoginResponseModel>> get() = _loginState

    fun registerUser(userRequest: UserRequest, onRegisterSuccess:()->Unit){
        viewModelScope.launch {
            _signUpState.value = Resource.Loading()
            try {
                val response = userRepository.registerUser(userRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _signUpState.value = Resource.Success(it)
                        onRegisterSuccess()
                        Log.d("authviewmodel", "registerUser: Registration successful")
                    } ?: run {
                        _signUpState.value = Resource.Error("Unexpected error: empty response body")
                    }
                } else {
                    _signUpState.value = Resource.Error(response.body()?.message ?: "Email already exits")
                }
            } catch (e: Exception) {
                _signUpState.value = Resource.Error("Exception occurred: ${e.message}")
            }
        }
    }

    fun loginUser(loginModel: LoginModel, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            try {
                val response = userRepository.loginUser(loginModel)
                handleResponse(response, onLoginSuccess)
            } catch (e: Exception) {
                _loginState.value = Resource.Error("Exception occurred: ${e.message}")
            }
        }
    }
    private fun handleResponse(response: Response<LoginResponseModel>, onLoginSuccess: () -> Unit) {
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                _loginState.value = Resource.Success(it)
                Log.d("RepositoryOrder", "Token when logged in ${it.accessToken}")
                authRepository.saveToken(it.accessToken)
                onLoginSuccess()
                Log.d("AuthViewModel", "loginUser: Login successful")
            } ?: run {
                _loginState.value = Resource.Error("Unexpected error")
            }
        } else {
            _loginState.value = Resource.Error(response.body()?.message ?: "User Not Found")
        }
    }
    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }
}
