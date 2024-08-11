package com.zip_feast.presentation.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.data.remote.models.serviceProviders.Data
import com.zip_feast.presentation.dashboard.viewmodels.ServiceProvidersViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.CustomSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ServiceProviderDetailScreen(
    navController: NavHostController,
    viewModel: ServiceProvidersViewModel = hiltViewModel<ServiceProvidersViewModel>(),
    onBackClick: () -> Unit,
    serviceProvider: Data?,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = { ProductTopAppBar(serviceProvider?.name.toString(), onBackClick) },
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 56.dp)
            ) { data ->
                CustomSnackBar(snackbarData = data)
            }
        },

        ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                ServiceDetail(
                    serviceProvider,
                    navController = navController,
                    snackBarState = snackbarHostState,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}

@Composable
fun ProductTopAppBar(productName: String, onBackClick: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 5.dp)
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = Icons.Outlined.ArrowBack,
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .clickable { onBackClick() },
            contentDescription = "back button"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = productName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(end = 64.dp)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "worker Name",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ServiceDetail(
    serviceProvider: Data?,
    navController: NavHostController,
    snackBarState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = serviceProvider?.avatar),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(12 / 10f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = serviceProvider?.name.toString(),
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rate " + serviceProvider?.price,
            fontSize = 16.sp,
            color = SkyBlue,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Email: ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = SkyBlue
                            )
                        ) {
                            append(serviceProvider?.email)
                        }
                    },
                    fontSize = 11.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "Phone: ${serviceProvider?.phone}",
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "Address: ${serviceProvider?.address}",
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Text(
                    text = "City: ${serviceProvider?.city}",
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Text(
                    text = "Pincode: ${serviceProvider?.pincode}",
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Text(
                    text = "state: ${serviceProvider?.state}",
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
            }

        }
        Text(
            text = buildAnnotatedString {
                append("Service: ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                    append(serviceProvider?.serviceType)
                }
            },
            fontSize = 9.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .width(9.dp)
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    snackBarState.showSnackbar(
                        message = "${serviceProvider?.name} is Booked Successfully",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = SkyBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Book Now",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}


