package com.zip_feast.presentation.dashboard.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CartScreen() {
   Box (
       modifier = Modifier.fillMaxSize(),
       contentAlignment = Alignment.Center
   ){
       Text(
           text = "CartScreen",
           color = Color.Blue
       )
   }

}