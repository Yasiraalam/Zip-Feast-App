package com.zip_feast.presentation.orders.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.CartOrderRequestModel
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.orders.viewmodel.PlaceOrderViewModel
import com.zip_feast.utils.apputils.Resource

@Composable
fun ShippingDetailsScreen(
    cartOrderRequestModel: CartOrderRequestModel,
    navController: NavHostController,
    placeOrderViewModel: PlaceOrderViewModel=  hiltViewModel<PlaceOrderViewModel>(),
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedPaymentMode by remember { mutableStateOf("Cash on Delivery") }

    val orderInformation by placeOrderViewModel.orderInformation.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(orderInformation) {
        when (orderInformation) {
            is Resource.Success -> {

                navController.navigate(Routes.OrderSuccessScreen.routes)
            }
            is Resource.Error -> {
                val message = (orderInformation as Resource.Error).errorMessage ?: "An unknown error occurred."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Handle other states if needed
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
                Text(
                    text = "Shipping Details",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Address") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedPaymentMode == "Pay Online",
                        onClick = { selectedPaymentMode = "Pay Online" }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pay Online")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedPaymentMode == "COD",
                        onClick = { selectedPaymentMode = "COD" }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cash on Delivery")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    val userOrder = cartOrderRequestModel.copy(
                        deliveryAddress = address,
                        paymentMethod = selectedPaymentMode
                    )
                    if (selectedPaymentMode == "Pay Online") {
                        "Order placed, payment mode: Pay Online"
                        Toast.makeText(context, "pay online first", Toast.LENGTH_SHORT).show()
                    } else {
                        placeOrderViewModel.placeOrder(userOrder)
                        navController.navigate(Routes.OrderSuccessScreen.routes)
                    }
                    
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = if (selectedPaymentMode == "Pay Online") "Pay Online" else "Place Order",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
