package com.zip_feast.presentation.dashboard.navigations

import com.zip_feast.data.remote.models.Data
import com.zip_feast.presentation.dashboard.navigations.navmodel.ProductDetail
import kotlinx.serialization.Serializable

sealed class Routes(val routes:String) {
    data object HomeScreen : Routes("HomeScreen")
    data object ServicesScreen : Routes("ServicesScreen")
    data object ExploreScreen : Routes("ExploreScreen")
    data object CartScreen : Routes("CartScreen")
    data object AccountScreen : Routes("AccountScreen")
    data class ProductDetailScreen(val productJson: String) : Routes("ProductDetailScreen/$productJson") {
        companion object {
            fun createRoute(productJson: String): String {
                return "ProductDetailScreen/$productJson"
            }
        }
    }
}

//sealed class Dest{
//
//    @Serializable
//
//}

