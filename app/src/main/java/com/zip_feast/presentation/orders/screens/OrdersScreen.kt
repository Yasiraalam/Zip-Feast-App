@file:OptIn(ExperimentalMaterial3Api::class)

package com.zip_feast.presentation.orders.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zip_feast.data.remote.models.ProfileModel.Order
import com.zip_feast.data.remote.models.ordersModels.ordersResponse.OrderDetails
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.orders.viewmodel.PlaceOrderViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun OrderScreen(
    navController: NavHostController,
    viewModel: PlaceOrderViewModel = hiltViewModel<PlaceOrderViewModel>(),
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserOrders()
    }

    val userOrdersState = viewModel.fetchUserOrders.collectAsState()
    val userOrders = userOrdersState.value.data?.data ?: emptyList()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Your Orders") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        content = {innerPadding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (userOrdersState.value) {
                    is Resource.Loading -> {
                        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
                    }
                    is Resource.Error -> {
                        Text(text = "${(userOrdersState.value as Resource.Error).errorMessage}", modifier = Modifier.padding(16.dp))
                    }
                    is Resource.Success -> {
                        if (userOrders.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(userOrders) { order ->
                                    OrderCard(
                                        orderDetails = order,
                                        orderStatus = order.orderStatus,
                                        paymentMethod = order.paymentMethod,
                                        totalAmount = order.totalAmount,
                                        totalQuantity = order.totalQuantity,
                                        deliveryAddress = order.deliveryAddress,
                                        navController = navController
                                    )
                                }
                            }
                        } else {
                            Text(text = "No Orders", modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    )


}


@Composable
fun OrderCard(
    orderStatus: String,
    paymentMethod: String,
    totalAmount: Int,
    totalQuantity: Int,
    deliveryAddress: String,
    navController: NavHostController,
    orderDetails: OrderDetails
) {
    val statusColor = when (orderStatus) {
        "PENDING", "ARRIVED" -> Color.Red
        "DELIVERED" -> SkyBlue
        else -> MaterialTheme.colorScheme.onSurface
    }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable {
                val orderJson = Json.encodeToString(orderDetails)
                navController.navigate(Routes.OrderDetailScreen.sendToDetail(orderJson))
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Address: $deliveryAddress",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Payement Mode: $paymentMethod",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order Status ",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = orderStatus,
                    style = MaterialTheme.typography.titleSmall,
                    color = statusColor,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Items",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = totalQuantity.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Rs: $totalAmount.00",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red
                )
            }
        }
    }
}
