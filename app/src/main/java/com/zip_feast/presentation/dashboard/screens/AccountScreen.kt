package com.zip_feast.presentation.dashboard.screens

import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zip_feast.LoginActivity
import com.zip_feast.R
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.presentation.dashboard.viewmodels.AccountViewModel
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.theme.Black
import com.zip_feast.presentation.theme.SkyBlue


sealed class IconType {
    data class VectorIcon(val imageVector: ImageVector) : IconType()
    data class DrawableIcon(val painter: Painter) : IconType()
}

@Composable
fun AccountScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel<AccountViewModel>(),
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Accounts",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp),
                    color = uiColor
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = uiColor
                    )
                }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextAndIconSection(iconType = IconType.VectorIcon(Icons.Outlined.Person), text = "Profile") {
                            navController.navigate(Routes.ProfileScreen.routes)
                    }

                    TextAndIconSection(iconType = IconType.DrawableIcon(painterResource(id = R.drawable.order_ic)), text = "Orders") {
                        navController.navigate(Routes.OrderScreen.routes)
                    }

                    TextAndIconSection(iconType = IconType.VectorIcon(Icons.Outlined.LocationOn), text = "Address") {
                        navController.navigate(Routes.ShipToScreen.routes)
                    }
                    TextAndIconSection(iconType = IconType.DrawableIcon(painterResource(id = R.drawable.payment_ic)), text = "Payment") {
                        // Handle click
                    }
                    TextAndIconSection(iconType = IconType.DrawableIcon(painterResource(id = R.drawable.logout_ic)), text = "Logout") {
                        showLogoutDialog = true
                    }
                    if (showLogoutDialog) {
                        AlertDialog(
                            onDismissRequest = { showLogoutDialog = false },
                            title = { Text("Confirm Logout") },
                            text = { Text("Are you sure you want to logout?") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        viewModel.logout {
                                            val loginIntent = Intent(context, LoginActivity::class.java)
                                            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            context.startActivity(loginIntent)
                                        }
                                        showLogoutDialog = false
                                    }
                                ) {
                                    Text("Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { showLogoutDialog = false }
                                ) {
                                    Text("No")
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TextAndIconSection(
    iconType: IconType,
    text: String,
    onClick: () -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        when (iconType) {
            is IconType.VectorIcon -> {
                Icon(
                    imageVector = iconType.imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = SkyBlue
                )
            }
            is IconType.DrawableIcon -> {
                Icon(
                    painter = iconType.painter,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = SkyBlue
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = uiColor
        )
    }
}
