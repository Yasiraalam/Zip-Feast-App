package com.zip_feast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zip_feast.presentation.auth.authnavigation.AuthNavigation
import com.zip_feast.presentation.theme.ZipFeastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipFeastTheme {
                AuthNavigation(onLoginSuccess = { navigateToZipFeastActivity() })
            }
        }
    }

    private fun navigateToZipFeastActivity() {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ZipFeastActivity::class.java)
        startActivity(intent)
        finish()
    }
}

