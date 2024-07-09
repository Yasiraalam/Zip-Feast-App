package com.zip_feast.presentation.dashboard.navigations

import ProductDetailScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zip_feast.presentation.dashboard.navigations.navmodel.ProductDetail
import com.zip_feast.presentation.profile.screens.AccountScreen
import com.zip_feast.presentation.cart.Screens.CartScreen
import com.zip_feast.presentation.dashboard.screens.ExploreScreen
import com.zip_feast.presentation.dashboard.screens.HomeScreen
import com.zip_feast.presentation.dashboard.screens.ServicesScreen
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController =navController,
        startDestination =  Routes.HomeScreen.routes,
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        composable(route = Routes.HomeScreen.routes) {
            HomeScreen(navController)
        }
        composable(route = Routes.ServicesScreen.routes) {
            ServicesScreen(navController)
        }
        composable(route = Routes.ExploreScreen.routes) {
            ExploreScreen()
        }
        composable(route = Routes.CartScreen.routes) {
            CartScreen()
        }
        composable(route = Routes.AccountScreen.routes) {
            AccountScreen()
        }

        composable(
            route = Routes.ProductDetailScreen.routes,
            arguments = listOf(navArgument("productJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = productJson?.let { Json.decodeFromString<ProductDetail>(it) }
            product?.let {
                ProductDetailScreen(product = it, onBackClick = { navController.navigateUp() })
            }
        }

    }
}


