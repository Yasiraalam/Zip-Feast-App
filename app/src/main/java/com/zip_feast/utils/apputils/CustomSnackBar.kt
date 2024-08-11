package com.zip_feast.utils.apputils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zip_feast.presentation.theme.SkyBlue

@Composable
fun CustomSnackBar(snackbarData: SnackbarData) {
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White),
        containerColor = Color.Gray,
        contentColor = Color.White,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        action = {
            Text(
                text = snackbarData.visuals.actionLabel ?: "",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { snackbarData.performAction() }
            )
        }
    ) {
        Text(
            text = snackbarData.visuals.message,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal
        )
    }
}