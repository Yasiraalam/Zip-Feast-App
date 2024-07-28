package com.zip_feast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zip_feast.models.BottomNav.BottomNavItem
import com.zip_feast.presentation.navigations.MyBottomBar
import com.zip_feast.presentation.navigations.NavGraph
import com.zip_feast.presentation.navigations.Routes
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
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                val routesToHideBottomBar = listOf(
                    Routes.ProductDetailScreen
                )
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            MyBottomBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    val adjustedPadding = PaddingValues(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        top = innerPadding.calculateTopPadding() - 8.dp,
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = innerPadding.calculateBottomPadding() - 40.dp
                    )
                    NavGraph(navController = navController, paddingValues = adjustedPadding)
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
        title = "Services",
        route = Routes.ServicesScreen.routes,
        selectedIcon = Icons.Filled.Build,
        unselectedIcon = Icons.Outlined.Build
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
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart
    ),
    BottomNavItem(
        title = "Account",
        route = Routes.AccountScreen.routes,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)


