package com.zip_feast.presentation.navigations

import android.net.Uri
import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.data.remote.models.ordersModels.CartOrderRequestModel
import com.zip_feast.data.remote.models.productsModels.Data
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Routes(val routes:String) {
    data object HomeScreen : Routes("HomeScreen")
    data object ServicesScreen : Routes("ServicesScreen")
    data object ExploreScreen : Routes("ExploreScreen")
    data object CartScreen : Routes("CartScreen")
    data object AccountScreen : Routes("AccountScreen")
    data object ProductDetailScreen : Routes("ProductDetailScreen/{product}") {
        fun createRoute(data: Data): String {
            val productJson = Json.encodeToString(data)
            return "ProductDetailScreen/${Uri.encode(productJson)}"
        }
    }
    data object ProfileScreen : Routes("ProfileScreen")
    data object SearchResultScreen : Routes("SearchResultScreen/{query}") {
        fun createRoute(query: String): String {
            return "SearchResultScreen/${Uri.encode(query)}"
        }
    }
    data object OrderScreen : Routes("OrderScreen")
    data object ShipToScreen : Routes("ShipToScreen")

    data object EditAddressScreen : Routes("EditAddressScreen/{userAddress}") {
        fun createRoute(userAddress: UserAddress): String {
            val userAddressJson = Json.encodeToString(userAddress)
            return "EditAddressScreen/${Uri.encode(userAddressJson)}"
        }
    }

    data object ShippingDetailsScreen:Routes("ShippingDetailsScreen/{orderDetails}") {
        fun sendToShip(cartOrderRequestModel: CartOrderRequestModel):String{
            val userOrder = Json.encodeToString(cartOrderRequestModel)
            return "ShippingDetailsScreen/${Uri.encode(userOrder)}"
        }
    }

    data object OrderSuccessScreen: Routes("OrderSuccessScreen")

    data object UserOrdersScreen:Routes("UserOrdersScreen/{orderDetails}") {
        fun sendToOrders(cartOrderRequestModel: CartOrderRequestModel):String{
            val userOrder = Json.encodeToString(cartOrderRequestModel)
            return "UserOrdersScreen/${Uri.encode(userOrder)}"
        }
    }

}
