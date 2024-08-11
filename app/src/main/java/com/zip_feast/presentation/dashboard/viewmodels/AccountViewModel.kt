package com.zip_feast.presentation.dashboard.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zip_feast.data.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val authRepository: AuthRepository
):ViewModel() {

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                authRepository.saveToken("")
                onLogoutComplete()
            } catch (e: Exception) {
                Log.e("ServiceProvidersViewModel", "Logout failed: ${e.message}")
            }
        }
    }
}