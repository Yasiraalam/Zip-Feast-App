package com.zip_feast.presentation.profile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zip_feast.data.remote.models.ProfileModel.UserAddress
import com.zip_feast.presentation.profile.viewmodel.ProfileViewModel

@Composable
fun EditAddressScreen(
    navController: NavHostController,
    userAddress: UserAddress,
    viewModel: ProfileViewModel
) {
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Text(
        text = "Update Address",
        modifier = Modifier.padding(top=26.dp, start = 16.dp),
        fontWeight = FontWeight.Bold
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 68.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 76.dp, start = 16.dp, end = 16.dp)
    ) {
        TextField(
            placeholder = {"State"},
            value = state,
            onValueChange = { state = it },
            label = { Text("State") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            placeholder = {"City"},
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            placeholder = {"Address"},
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            placeholder = {"Phone"},
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val updatedAddress = UserAddress(state, address, city, phone)
                viewModel.updateUserAddress(updatedAddress)
                navController.navigateUp()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}
