@file:OptIn(ExperimentalMaterial3Api::class)

package com.zip_feast.presentation.orders.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.zip_feast.data.remote.models.ordersModels.CartOrderRequestModel
import com.zip_feast.presentation.orders.viewmodel.PlaceOrderViewModel
import com.zip_feast.presentation.theme.SkyBlue

@Composable
fun OrderScreen(
    navController: NavHostController,
    viewModel: PlaceOrderViewModel = hiltViewModel<PlaceOrderViewModel>(),
) {
    val userOrdersState = viewModel.fetchUserOrders.collectAsState()
    val userOrders = userOrdersState.value.data?.data ?: emptyList()
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Order") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        if(userOrders.isEmpty()){
            OrderCard(
                orderStatus = userOrders.first().orderStatus,
                paymentMethod = userOrders.first().paymentMethod,
                totalAmount = userOrders.first().totalAmount,
                totalQuantity = userOrders.first().totalQuantity,
                deliveryAddress = userOrders.first().deliveryAddress
            )
        }else{
            Text(text = "No  Orders")
        }
      
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun OrderCard(
    orderStatus: String,
    paymentMethod: String,
    totalAmount: Int,
    totalQuantity: Int,
    deliveryAddress: String
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = deliveryAddress,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Payement Mode: $paymentMethod",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order Status $orderStatus",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Quentity: $totalQuantity",
                    style = MaterialTheme.typography.titleSmall,
                    color = SkyBlue,
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
                    text = " items purchased",
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
                    text = "Total: $totalAmount",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${String.format("%.2f", totalAmount)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = SkyBlue
                )
            }
        }
    }
}
