package com.zip_feast.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zip_feast.ui.auth.screens.RegistrationScreen
import com.zip_feast.ui.theme.ZipFeastTheme

class ZipFeastActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZipFeastTheme {
//                LoginScreen()
                RegistrationScreen()
            }
        }
    }
}

