package com.zip_feast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zip_feast.presentation.theme.ZipFeastTheme
import com.zip_feast.utils.authnavigation.AuthNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZipFeastActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZipFeastTheme {
               AuthNavigation()
            }
        }
    }
}

