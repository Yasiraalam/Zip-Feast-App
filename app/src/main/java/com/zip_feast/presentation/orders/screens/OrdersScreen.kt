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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zip_feast.presentation.theme.SkyBlue

@Composable
fun OrderScreen(navHostController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Order") },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OrderCard(
            orderNumber = "SDG1345KJD",
            orderDate = "August 1, 2017",
            orderStatus = "Shipping",
            itemsPurchased = 1,
            price = 299.43
        )
        Spacer(modifier = Modifier.height(8.dp))
        OrderCard(
            orderNumber = "SDG1345KJD",
            orderDate = "August 1, 2017",
            orderStatus = "Shipping",
            itemsPurchased = 1,
            price = 299.43
        )
        Spacer(modifier = Modifier.height(8.dp))
        OrderCard(
            orderNumber = "SDG1345KJD",
            orderDate = "August 1, 2017",
            orderStatus = "Shipping",
            itemsPurchased = 1,
            price = 299.43
        )
    }
}

@Composable
fun OrderCard(
    orderNumber: String,
    orderDate: String,
    orderStatus: String,
    itemsPurchased: Int,
    price: Double
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
                text = orderNumber,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Order at E-com: $orderDate",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order Status",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = orderStatus,
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
                    text = "$itemsPurchased items purchased",
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
                    text = "Price",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${String.format("%.2f", price)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = SkyBlue
                )
            }
        }
    }
}
