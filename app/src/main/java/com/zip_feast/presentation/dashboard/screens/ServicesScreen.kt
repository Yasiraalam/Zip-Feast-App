package com.zip_feast.presentation.dashboard.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.R
import com.zip_feast.data.remote.models.serviceProviders.Data
import com.zip_feast.data.remote.models.serviceProviders.ServiceProviderDetailResponse
import com.zip_feast.presentation.dashboard.viewmodels.ServiceProvidersViewModel
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource

@Composable
fun ServicesScreen(
    navController: NavHostController,
    viewModel: ServiceProvidersViewModel = hiltViewModel()
) {
    val serviceProviders = viewModel.serviceProviders.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllServiceProviders()
    }
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Service Providers",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp),
                    color = uiColor
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = uiColor
                    )
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            when (val state = serviceProviders.value) {
                is Resource.Success -> {
                    val serviceProvidersList = state.data?.data ?: emptyList()
                    items(serviceProvidersList) { serviceProvider ->
                        ServicesScreenItem(
                            navController = navController,
                            serviceProvider = serviceProvider
                        )
                    }
                }

                is Resource.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = "Loading...", color = uiColor, modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }

                is Resource.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No Service Provider Available Now!", color = uiColor, modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ServicesScreenItem(
    navController: NavHostController,
    serviceProvider: Data
) {
    val uiColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = uiColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate(
                        Routes.ServiceProviderDetailScreen.sendToDetail(serviceProvider)
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = serviceProvider.avatar
                ),
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentDescription = "Worker Image"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(180.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = serviceProvider.name,
                    maxLines = 1,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(modifier = Modifier.width(160.dp))
                Text(
                    text = "Rate: ${serviceProvider.price}",
                    color = SkyBlue,
                    fontSize = 12.sp,
                )
                Text(
                    text = "Phone: ${serviceProvider.phone}",
                    color = SkyBlue,
                    fontSize = 12.sp,
                )
                Text(
                    text = "Email: ${serviceProvider.email}",
                    color = Color.Black,
                    fontSize = 12.sp,
                )
                Text(
                    text = buildAnnotatedString {
                        append("Service: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                            append(serviceProvider.serviceType)
                        }
                    },
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            val context = LocalContext.current
            IconButton(
                onClick = {
                    val phoneNumber = serviceProvider.phone
                    if (phoneNumber != null) {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = "Call",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(44.dp)
                )
            }
        }
    }

}