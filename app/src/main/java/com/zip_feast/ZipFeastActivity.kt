package com.zip_feast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.zip_feast.presentation.dashboard.BottomNav.BottomNavItem
import com.zip_feast.presentation.dashboard.navigations.MyBottomBar
import com.zip_feast.presentation.dashboard.navigations.NavGraph
import com.zip_feast.presentation.dashboard.navigations.Routes
import com.zip_feast.presentation.theme.ZipFeastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZipFeastActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZipFeastTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        NavigationBar{
                           MyBottomBar(navController = navController)
                        }
                    }
                ){
                    val padding  = it
                    NavGraph(navController = navController)
                }
            }
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        route = Routes.HomeScreen.routes,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        title = "Explore",
        route = Routes.ExploreScreen.routes,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    ),
    BottomNavItem(
        title = "Cart",
        route = Routes.CartScreen.routes,
        selectedIcon = Icons.Filled.ShoppingCart ,
        unselectedIcon = Icons.Outlined.ShoppingCart
    ),
    BottomNavItem(
        title = "Account",
        route = Routes.AccountScreen.routes,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)


