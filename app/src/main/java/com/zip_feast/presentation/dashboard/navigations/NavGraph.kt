package com.zip_feast.presentation.dashboard.navigations

import ProductDetailScreen
import android.util.Log
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
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.zip_feast.data.remote.models.Data
import com.zip_feast.models.FlashSaleItem
import com.zip_feast.presentation.dashboard.navigations.navmodel.ProductDetail
import com.zip_feast.presentation.profile.screens.AccountScreen
import com.zip_feast.presentation.cart.Screens.CartScreen
import com.zip_feast.presentation.dashboard.screens.ExploreScreen
import com.zip_feast.presentation.dashboard.screens.HomeScreen
import com.zip_feast.presentation.dashboard.screens.ServicesScreen
import com.zip_feast.utils.apputils.CustomNavType
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.routes,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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
            route = Routes.ProductDetailScreen.createRoute("{productJson}"),
            arguments = listOf(navArgument("productJson") { type = CustomNavType(Data::class, Data.serializer()) })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = productJson?.let {
                try {
                    Json.decodeFromString<Data>(it)
                } catch (e: Exception) {
                    null // Handle JSON parsing error
                }
            }
            product?.let {
                ProductDetailScreen(product = product, onBackClick = { navController.navigateUp() })
            }
        }
//        composable<Dest.ProductDetailScreen>(
//            typeMap = mapOf(
//                typeOf<Data>() to CustomNavType<Data>(
//                    Data::class,
//                    Data.serializer()
//                )
//            )
//        ) {
//            val product = it.toRoute<Dest.ProductDetailScreen>()
//            ProductDetailScreen(product, onBackClick = { navController.navigateUp() })
//
//        }

    }
}
