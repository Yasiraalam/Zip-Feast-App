@file:OptIn(ExperimentalMaterial3Api::class)

package com.zip_feast.presentation.orders.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.data.remote.models.ordersModels.ordersResponse.OrderDetails
import com.zip_feast.presentation.orders.viewmodel.PlaceOrderViewModel
import com.zip_feast.presentation.theme.SkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserOrderDetailsScreen(
    orderData: OrderDetails?,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed.invoke()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                ProductDetail(orderData = orderData)
            }
        }
    }
}

@Composable
fun ProductDetail(orderData: OrderDetails?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductProgressIndicator(currentStep = 0)
    }

    ProductCard(
        productName = orderData?.items?.first()?.product?.name.toString(),
        productImage = orderData?.items?.first()?.product?.productImage,
        productPrice = orderData?.items?.first()?.product?.price.toString(),
        quantity = orderData?.totalQuantity.toString().toInt()
    )

    // Shipping Details Section
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Shipping Details",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            ShippingDetailsField(label = "Date Shipping", value = orderData?.createdAt.toString())
            ShippingDetailsField(label = "Shipping", value = orderData?.deliveryAddress.toString())
            ShippingDetailsField(label = "Payment Type", value = orderData?.paymentMethod.toString())
            ShippingDetailsField(label = "Address", value = orderData?.deliveryAddress.toString())
            ShippingDetailsField(label = "Order Status", value = orderData?.orderStatus.toString())
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Payment Details",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 12.dp)
    )
    OrderShippingSection(
        totalQuantity = orderData?.totalQuantity?: 0,
        totalPrice = orderData?.totalAmount?.toDouble() ?: 0.0
    )
    val context = LocalContext.current
    Button(
        onClick = { Toast.makeText(context, "Ok Will Notify", Toast.LENGTH_SHORT).show() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF56D4FD)
        )
    ) {
        Text(
            text = "Notify Me",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductProgressIndicator(currentStep: Int) {
    val steps = listOf(
        Pair(currentStep == 0, Color(0xFF56D4FD)),
        Pair(currentStep == 1, Color.Gray),
        Pair(currentStep == 2, Color.Gray),
        Pair(currentStep == 3, Color.Gray)
    )
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        steps.forEach { (isActive, color) ->
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(if (isActive) color else Color.LightGray)
            )
        }
    }
}

@Composable
fun ProductCard(
    productName: String,
    productImage: String?,
    productPrice: String,
    quantity: Int
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
                Image(
                    painter = rememberAsyncImagePainter(model = productImage),
                    contentDescription = productName,
                    modifier = Modifier.size(64.dp)
                )

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = productName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$$productPrice",
                    fontSize = 14.sp,
                    color = Color(0xFF56D4FD)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = quantity.toString())
            }
        }
    }
}

@Composable
fun ShippingDetailsField(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            fontSize = 13.sp
        )
    }
}

@Composable
fun OrderShippingSection(totalQuantity: Int, totalPrice: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Items(${totalQuantity})", fontSize = 10.sp, color = Color.Gray)
                Text(text = "Rs $totalPrice", fontSize = 10.sp,fontWeight = FontWeight.Bold)
            }
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Shipping", fontSize = 10.sp, color = Color.Gray)
                Text(text = "Rs ${40}", fontSize = 10.sp,fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Import Charges", fontSize = 10.sp, color = Color.Gray)
                Text(text = "Rs ${30}", fontSize = 10.sp,fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total Price", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Rs: $totalPrice", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SkyBlue)

            }
        }

    }
}