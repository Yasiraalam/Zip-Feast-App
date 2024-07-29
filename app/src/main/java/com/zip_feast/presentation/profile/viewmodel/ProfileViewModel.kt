package com.zip_feast.presentation.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.ProfileModel.UserProfileResponse
import com.zip_feast.data.remote.models.userUpdateModels.UpdateProfileRequest
import com.zip_feast.data.remote.models.userUpdateModels.UserInfoUpdate
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.data.remote.repository.UserRepository
import com.zip_feast.utils.apputils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _profile = MutableLiveData<Resource<UserProfileResponse>>()
    val profile: LiveData<Resource<UserProfileResponse>> = _profile

    private val _updatedprofile = MutableLiveData<Resource<UserProfileResponse>>()
    val updatedprofile: LiveData<Resource<UserProfileResponse>> = _updatedprofile

    fun fetchUserProfile() {
        val token = authRepository.getToken()
        if (token != null) {
            _profile.value = Resource.Loading()
            viewModelScope.launch {
                try {
                    val result = userRepository.getUserProfile(token)
                    _profile.value = result
                    when (result) {
                        is Resource.Success -> {
                            Log.d(
                                "ProfileViewModel",
                                "fetchUserProfile: Profile fetched successfully: ${result.data}"
                            )
                        }
                        is Resource.Error -> {
                            Log.e(
                                "ProfileViewModel",
                                "fetchUserProfile: Error fetching profile: ${result.errorMessage}"
                            )
                        }
                        is Resource.Loading -> {
                            Log.d("ProfileViewModel", "fetchUserProfile: Loading...")
                        }
                    }
                } catch (e: Exception) {
                    _profile.value = Resource.Error("An unknown error occurred")
                    Log.e("ProfileViewModel", "fetchUserProfile: Exception: ${e.message}", e)
                }
            }
        } else {
            Log.d("ProfileViewModel", "fetchUserProfile: Token is null")
        }
    }
    fun updateUserProfile(userInfoUpdate: UserInfoUpdate) {
        viewModelScope.launch {
            val token = authRepository.getToken()
            if (token!=null){
                  val result = userRepository.updateUserProfile(token, userInfoUpdate)
                  _updatedprofile.postValue(result)
                  fetchUserProfile()
            }else{
                Log.d("ProfileViewModel", "fetchUserProfile: Token is null")
            }

        }
    }
}
