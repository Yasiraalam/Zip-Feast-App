package com.zip_feast

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zip_feast.presentation.auth.authnavigation.AuthNavigation
import com.zip_feast.presentation.theme.ZipFeastTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUserLoggedIn()) {
            navigateToZipFeastActivity()
        } else {
            setContent {
                ZipFeastTheme {
                    AuthNavigation(onLoginSuccess = { navigateToZipFeastActivity() })
                }
            }
        }
    }
    private fun isUserLoggedIn(): Boolean {
        val token = sharedPreferences.getString("auth_token", null)
        return !token.isNullOrEmpty()
    }
    private fun navigateToZipFeastActivity() {
        val intent = Intent(this, ZipFeastActivity::class.java)
        startActivity(intent)
        finish()
    }
}

