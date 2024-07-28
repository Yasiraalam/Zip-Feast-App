package com.zip_feast.models.BottomNav


import androidx.compose.ui.graphics.vector.ImageVector
import com.zip_feast.presentation.dashboard.navigations.Routes

data class BottomNavItem(
    val title:String,
    val route:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
