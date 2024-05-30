package com.zip_feast.presentation.dashboard.navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zip_feast.presentation.dashboard.screens.AccountScreen
import com.zip_feast.presentation.dashboard.screens.CartScreen
import com.zip_feast.presentation.dashboard.screens.ExploreScreen
import com.zip_feast.presentation.dashboard.screens.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController =navController,
        startDestination =  Routes.HomeScreen.routes,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Routes.HomeScreen.routes) {
            HomeScreen(navController)
        }
        composable(route = Routes.ExploreScreen.routes) {
            ExploreScreen()
        }
        composable(route = Routes.CartScreen.routes) {
            CartScreen()
        }
        composable(route = Routes.AccountScreen.routes) {
            AccountScreen(navController)
        }
    }
}