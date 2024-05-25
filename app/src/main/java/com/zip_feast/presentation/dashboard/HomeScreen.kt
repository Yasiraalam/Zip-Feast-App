@file:OptIn(ExperimentalMaterial3Api::class)

package com.zip_feast.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    HomeScreen()
}
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box {
        Scaffold(
            topBar = { topAppBar() },
            containerColor = MaterialTheme.colorScheme.primary,
        ) { paddingValues ->
            Content(paddingValues)

        }
    }
}

@Composable
fun Content(paddingValues: PaddingValues) {

}

@Composable
fun topAppBar() {
    val searchText = remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText.value,
            onValueChange = { newValue -> searchText.value = newValue },
            label = { Text(text = "Search Food, grocery etc.", fontSize = 12.sp) },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
        )
    }

}
