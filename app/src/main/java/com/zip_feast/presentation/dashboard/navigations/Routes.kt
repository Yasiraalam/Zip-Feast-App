package com.zip_feast.presentation.dashboard.navigations

sealed class Routes(val routes:String) {
    data object HomeScreen : Routes("HomeScreen")
    data object ExploreScreen : Routes("ExploreScreen")
    data object CartScreen : Routes("CartScreen")
    data object Offer : Routes("Offer")
    data object AccountScreen : Routes("AccountScreen")

}