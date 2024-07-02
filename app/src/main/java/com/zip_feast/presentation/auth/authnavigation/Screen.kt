package com.zip_feast.presentation.auth.authnavigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
}
