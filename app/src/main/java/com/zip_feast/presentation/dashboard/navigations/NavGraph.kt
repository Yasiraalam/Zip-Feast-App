package com.zip_feast.presentation.dashboard.navigations

import ProductDetailScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zip_feast.presentation.dashboard.navigations.navmodel.ProductDetail
import com.zip_feast.presentation.dashboard.screens.AccountScreen
import com.zip_feast.presentation.dashboard.screens.CartScreen
import com.zip_feast.presentation.dashboard.screens.ExploreScreen
import com.zip_feast.presentation.dashboard.screens.HomeScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
            AccountScreen()
        }

        composable(
            route = Routes.ProductDetailScreen.routes,
            arguments = listOf(navArgument("productJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = Json.decodeFromString<ProductDetail>(productJson!!)
            ProductDetailScreen(product = product, onBackClick = { navController.navigateUp() })
        }
    }
}


