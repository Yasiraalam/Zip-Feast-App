//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.zip_feast.presentation.orders.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.CartOrderRequestModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun OrderDetailsScreen(
//    placeOrderViewModel: CartOrderRequestModel,
//    navController: NavHostController,
//    onBackClick: () -> Unit
//) {
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Order Details") },
//                navigationIcon = {
//                    IconButton(onClick = { /* Handle back button click */ }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // Product Progress Indicator
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                ProductProgressIndicator(currentStep = 0)
//            }
//
//            // Product Section
//            ProductCard(
//                productName = "Nike Air Zoom Pegasus 36 Miami",
//                productImage = "",
//                productPrice = "299,43",
//                quantity = 1
//            )
//            ProductCard(
//                productName = "Nike Air Zoom Pegasus 36 Miami",
//                productImage = painterResource(id = productImage) ,
//                productPrice = "299,43",
//                quantity = 1
//            )
//
//            // Shipping Details Section
//            Card(
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 4.dp
//                ),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Shipping Details",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Shipping Details Fields
//                    ShippingDetailsField(label = "Date Shipping", value = "January 16, 2020")
//                    ShippingDetailsField(label = "Shipping", value = "POS Regguular")
//                    ShippingDetailsField(label = "No. Resi", value = "000192848573")
//                    ShippingDetailsField(label = "Address", value = "2727 New Owerri, Owerri, Imo State 70410")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { /* Handle Notify Me button click */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = Color(0xFF56D4FD)
//                )
//            ) {
//                Text(
//                    text = "Notify Me",
//                    color = Color.White,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ProductProgressIndicator(currentStep: Int) {
//    val steps = listOf(
//        Pair(currentStep == 0, Color(0xFF56D4FD)),
//        Pair(currentStep == 1, Color.Gray),
//        Pair(currentStep == 2, Color.Gray),
//        Pair(currentStep == 3, Color.Gray)
//    )
//    Row(
//        modifier = Modifier
//            .padding(vertical = 16.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        steps.forEach { (isActive, color) ->
//            Box(
//                modifier = Modifier
//                    .size(30.dp)
//                    .clip(CircleShape)
//                    .background(if (isActive) color else Color.LightGray)
//            )
//        }
//    }
//}
//
//@Composable
//fun ProductCard(
//    productName: String,
//    productImage: Int,
//    productPrice: String,
//    quantity: Int
//) {
//    Card(
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 4.dp
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(id = productImage),
//                contentDescription = productName,
//                modifier = Modifier.size(64.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = productName,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "$$productPrice",
//                    fontSize = 14.sp,
//                    color = Color(0xFF56D4FD)
//                )
//            }
//            Spacer(modifier = Modifier.width(16.dp))
//            // Add to Cart and Delete Buttons
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { /* Handle Add to Cart click */ }) {
//                    Icon(Icons.Filled.Favorite, contentDescription = "Add to Cart", tint = Color(0xFF56D4FD))
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                IconButton(onClick = { /* Handle Delete click */ }) {
//                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedTextField(
//                    value = quantity.toString(),
//                    onValueChange = { /* Handle Quantity Change */ },
//                    readOnly = true,
//                    modifier = Modifier.width(50.dp),
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ShippingDetailsField(label: String, value: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = label,
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.weight(1f))
//        Text(
//            text = value,
//            fontSize = 14.sp
//        )
//    }
//}
