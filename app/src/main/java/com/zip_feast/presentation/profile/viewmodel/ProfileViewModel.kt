package com.zip_feast.presentation.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.data.remote.models.ProfileModel.UserProfileResponse
import com.zip_feast.data.remote.models.userUpdateModels.UserUpdateResModel
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

    private val _userUpdatedProfile = MutableLiveData<Resource<UserUpdateResModel>>()
    val userUpdatedProfile: LiveData<Resource<UserUpdateResModel>> = _userUpdatedProfile
    init {
        fetchUserProfile()
    }

    private val _updatedAddress = MutableLiveData<Resource<UserProfileResponse>>()
    val updatedAddress: LiveData<Resource<UserProfileResponse>> = _updatedAddress


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
                                "fetchUserProfile",
                                "fetchUserProfile: Profile fetched successfully: ${result.data}"
                            )
                        }
                        is Resource.Error -> {
                            Log.e(
                                "fetchUserProfile",
                                "fetchUserProfile: Error fetching profile: ${result.errorMessage}"
                            )
                        }
                        is Resource.Loading -> {
                            Log.d("fetchUserProfile", "fetchUserProfile: Loading...")
                        }
                    }
                } catch (e: Exception) {
                    _profile.value = Resource.Error("An unknown error occurred")
                    Log.e("fetchUserProfile", "fetchUserProfile: Exception: ${e.message}", e)
                }
            }
        } else {
            Log.d("fetchUserProfile", "fetchUserProfile: Token is null")
        }
    }

    fun updateUserProfile(userInfoUpdate: UserInfoUpdate) {
        viewModelScope.launch {
            val token = authRepository.getToken()
            if (token!=null){
                  val result = userRepository.updateUserProfile(token=token, userInfoUpdate =userInfoUpdate)
                _userUpdatedProfile.value = result
                  fetchUserProfile()
            }else{
                Log.d("updateUserProfile", "fetchUserProfile: Token is null")
            }
        }
    }
    
    fun updateUserAddress(userAddress: UserAddress) {
        viewModelScope.launch {
            val token = authRepository.getToken()
            if (token!=null){
                val result = userRepository.updateUserAddress(token=token, userAddress =userAddress)
                Log.d("Address", "Address: ${result.data?.data}")
                _updatedAddress.postValue(result)
                fetchUserProfile()
            }else{
                Log.d("ProfileViewModel", "fetchUserProfile: Token is null")
            }

        }
    }
}
