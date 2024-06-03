package com.zip_feast.presentation.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { ProductTopAppBar()},
        containerColor = Color.White
    ) { innerPadding ->
        val padding = innerPadding
        ProductDetail()

    }
}
@Composable
fun ProductTopAppBar() {
    var searchText by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(top = 50.dp, start = 10.dp, end = 10.dp)
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back button")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "product name",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "MoreVert",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ProductDetail(modifier: Modifier = Modifier) {
    
}