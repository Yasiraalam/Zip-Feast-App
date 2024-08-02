package com.zip_feast.presentation.orders.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zip_feast.presentation.theme.SkyBlue

@Composable
fun SuccessScreen(
    navController: NavHostController,
    onbackClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.White
    ) {innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = SkyBlue,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Success",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "thank you for shopping using ZipFeast",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    onbackClick.invoke()
                },
                modifier = Modifier.fillMaxWidth(0.5f).height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Back To Order")
            }
        }
    }
}