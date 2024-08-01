@file:OptIn(ExperimentalMaterial3Api::class)

package com.zip_feast.presentation.profile.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.profile.viewmodel.ProfileViewModel
import com.zip_feast.utils.apputils.Resource

@Composable
fun ShipToScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
    ) {
    LaunchedEffect(Unit) {
        viewModel.updatedAddress
    }
    val userAddress = viewModel.updatedAddress.observeAsState(Resource.Loading())
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopAppBar(
            title = { Text("Ship To") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { /* Handle add action */ }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ShipToCard(
            state = userAddress.value.data?.data?.state ?: "No state available",
            address = userAddress.value.data?.data?.address ?: "No address available",
            phoneNumber = userAddress.value.data?.data?.phone ?: "No phone number available",
            city = userAddress.value.data?.data?.city ?: "No city available",
            navController = navController
        )
        Button(
            onClick = { /* Handle next action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF64C8FF)
            )
        ) {
            Text(
                text = "Next",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ShipToCard(
    state: String?,
    address: String?,
    city:String?,
    phoneNumber: String?,
    navController: NavHostController

) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = state?:"state",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = address?:"address",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = phoneNumber?:"Phone number",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    val userAddress = UserAddress(
                        state = state,
                        address = address,
                        city = city,
                        phone = phoneNumber
                    )
                    navController.navigate(Routes.EditAddressScreen.createRoute(userAddress))
                }) {
                    Text(
                        text = "Edit",
                        color = Color.Blue
                    )
                }
                TextButton(onClick = { /* Handle delete action */ }) {
                    Text(
                        text = "Delete",
                        color = Color(0xFFF44336) // Red color
                    )
                }
            }
        }
    }
}