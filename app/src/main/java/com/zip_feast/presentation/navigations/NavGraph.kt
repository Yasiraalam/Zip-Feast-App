package com.zip_feast.presentation.navigations

import ProductDetailScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.CartOrderRequestModel
import com.zip_feast.data.remote.models.productsModels.Data
import com.zip_feast.data.remote.models.serviceProviders.ServiceProviderDetailResponse
import com.zip_feast.presentation.cart.Screens.CartScreen
import com.zip_feast.presentation.dashboard.screens.ExploreScreen
import com.zip_feast.presentation.dashboard.screens.HomeScreen
import com.zip_feast.presentation.dashboard.screens.ServicesScreen
import com.zip_feast.presentation.dashboard.screens.AccountScreen
import com.zip_feast.presentation.dashboard.screens.ServiceProviderDetailScreen
import com.zip_feast.presentation.orders.screens.OrderScreen
import com.zip_feast.presentation.orders.screens.ShippingDetailsScreen
import com.zip_feast.presentation.orders.screens.SuccessScreen
import com.zip_feast.presentation.profile.screens.EditAddressScreen
import com.zip_feast.presentation.profile.screens.ProfileScreen
import com.zip_feast.presentation.profile.screens.ShipToScreen
import com.zip_feast.presentation.profile.viewmodel.ProfileViewModel
import kotlinx.serialization.json.Json

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
            CartScreen(navController = navController)
        }
        composable(route = Routes.AccountScreen.routes) {
            AccountScreen(navController)
        }
        composable(
            route = Routes.ProductDetailScreen.routes,
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("product")
            val product = Json.decodeFromString<Data>(productJson!!)
            ProductDetailScreen(product = product, navController = navController) {
                navController.navigateUp()
            }
        }
        composable(route = Routes.ProfileScreen.routes) {
            ProfileScreen(navController = navController)
        }
        composable(route = Routes.ShipToScreen.routes) {
            ShipToScreen(navController = navController)
        }

        composable(
            route = Routes.EditAddressScreen.routes,
            arguments = listOf(navArgument("userAddress") { type = NavType.StringType })
        ) { backStackEntry ->
            val userAddressJson = backStackEntry.arguments?.getString("userAddress")
            val userAddress = Json.decodeFromString<UserAddress>(userAddressJson!!)
            val viewModel = hiltViewModel<ProfileViewModel>()
            EditAddressScreen(
                navController = navController,
                userAddress = userAddress,
                viewModel = viewModel
            )
        }

        composable(
            route = Routes.ShippingDetailsScreen.routes,
            arguments = listOf(navArgument("orderDetails") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val orderDetailsJson = navBackStackEntry.arguments?.getString("orderDetails")
            val cartOrderRequestModel =
                orderDetailsJson?.let { Json.decodeFromString<CartOrderRequestModel>(it) }

            cartOrderRequestModel?.let {
                ShippingDetailsScreen(cartOrderRequestModel = it, navController = navController) {
                    navController.navigateUp()
                }
            }
        }
        composable(route = Routes.OrderSuccessScreen.routes) {
            SuccessScreen(navController = navController) {
                navController.navigateUp()
            }
        }
        composable(route = Routes.OrderScreen.routes) {
            OrderScreen(navController = navController)
        }
        composable(
            route = Routes.ServiceProviderDetailScreen.routes,
            arguments = listOf(navArgument("orderDetail") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderDetailJson = backStackEntry.arguments?.getString("orderDetail")
            val orderDetail = orderDetailJson?.let { Json.decodeFromString<com.zip_feast.data.remote.models.serviceProviders.Data>(it) }

            ServiceProviderDetailScreen(
                navController = navController,
                serviceProvider = orderDetail,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

    }
}
