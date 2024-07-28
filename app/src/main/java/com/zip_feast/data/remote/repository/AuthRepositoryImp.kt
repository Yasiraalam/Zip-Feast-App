package com.zip_feast.data.remote.repository

import android.content.SharedPreferences
import javax.inject.Inject

interface AuthRepository {
     fun getToken(): String?
     fun saveToken(token: String)
     fun isLoggedIn(): Boolean
}

class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override  fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    override  fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    override fun isLoggedIn(): Boolean {
        return getToken()!=null
    }

}