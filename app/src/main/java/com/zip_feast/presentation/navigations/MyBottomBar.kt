package com.zip_feast.presentation.navigations

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zip_feast.bottomNavItems
import com.zip_feast.presentation.theme.SkyBlue

@Composable
fun MyBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination?.route
    val uiColor = if(isSystemInDarkTheme()) Color.White else Color.Black
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentDestination ==item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = if (selected) SkyBlue else uiColor
                    )
                },
                label = {
                    Text(
                        fontSize = 10.sp,
                        text = item.title,
                        color = if (selected) SkyBlue else uiColor
                    )
                }
            )
        }
    }
}
