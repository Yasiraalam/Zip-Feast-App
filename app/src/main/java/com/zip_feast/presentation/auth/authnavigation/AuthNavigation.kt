package com.zip_feast.presentation.auth.authnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zip_feast.presentation.auth.screens.LoginScreen
import com.zip_feast.presentation.auth.screens.RegistrationScreen

@Composable
fun AuthNavigation(onLoginSuccess: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onClick ={onLoginSuccess()}
            )
        }
        composable(Screen.Register.route) {
            RegistrationScreen(navController = navController)
        }
    }
}



