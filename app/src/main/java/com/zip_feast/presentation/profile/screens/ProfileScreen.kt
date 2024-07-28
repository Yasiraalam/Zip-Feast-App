package com.zip_feast.presentation.profile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.zip_feast.presentation.profile.ProfileViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource


@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState = viewModel.profile.observeAsState(initial = Resource.Loading())

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }
    Scaffold(
        topBar = {
            ProfileTopSection(){
                navController.navigateUp()
            }
        }
    ) {innerPadding->
        when (profileState.value) {
            is Resource.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Loading...")
                }
            }
            is Resource.Success -> {
                // Show profile data
                val profile = (profileState.value as Resource.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            start = 15.dp,
                            end = 15.dp
                        )
                ) {
                    ProfileHeader(
                        name = profile?.data?.name ?: "Unknown",
                        avatarUrl = profile?.data?.avatar.toString(),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    ProfileItem(icon = Icons.Default.Call, label = "Phone Number", value = (profile?.data?.phone ?: "+91XXXXXXXXXX").toString())
                    ProfileItem(icon = Icons.Default.Email, label = "Email", value = (profile?.data?.email ?: "example@gmail.com"))
                    ProfileItem(icon = Icons.Default.Info, label = "Address", value = ((profile?.data?.address ?: "India").toString()))
                }
            }
            is Resource.Error -> {
                // Show error message
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            start = 15.dp,
                            end = 15.dp
                        )
                ) {
                    ProfileHeader(
                        name = "Unknown",
                        avatarUrl = "@username",
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    ProfileItem(icon = Icons.Default.Call, label = "Phone Number", value = "+91XXXXXXXXXX")
                    ProfileItem(icon = Icons.Default.Email, label = "Email", value = "example@gmail.com")
                    ProfileItem(icon = Icons.Default.Info, label = "Address", value = "India")
                }
            }
        }
    }
}
@Composable
fun ProfileTopSection(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
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
            text = "Profile",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
@Composable
fun ProfileHeader(name: String, avatarUrl: String) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        val imagePainter = if (avatarUrl.isNullOrEmpty()) {
            rememberImagePainter(Icons.Default.Person)
        } else {
            rememberImagePainter(avatarUrl)
        }
        Image(
            painter =   imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "@$name",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileItem(icon: ImageVector, label: String, value: String) {
    val uiColor = if(isSystemInDarkTheme()) Color.White else SkyBlue
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = uiColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = uiColor,
            modifier = Modifier.size(24.dp)
        )
    }
}



