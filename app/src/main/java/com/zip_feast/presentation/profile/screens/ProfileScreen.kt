package com.zip_feast.presentation.profile.screens


import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.zip_feast.data.remote.models.userUpdateModels.UserInfoUpdate
import com.zip_feast.presentation.profile.viewmodel.ProfileViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource

enum class EditableField {
    PHONE, EMAIL, ADDRESS, NONE
}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
) {
    val profileState = viewModel.profile.observeAsState(initial = Resource.Loading())
    val updatedProfileState = viewModel.userUpdatedProfile.observeAsState(initial = Resource.Loading())

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }

    var editingField by remember {
        mutableStateOf(EditableField.NONE)
    }
    var tempPhone by remember { mutableStateOf("") }
    var tempEmail by remember { mutableStateOf("") }
    var tempAddress by remember { mutableStateOf("") }

    var updateButtonEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ProfileTopSection() {
                navController.navigateUp()
            }
        }
    ) { innerPadding ->
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
                val profile = (profileState.value as Resource.Success).data
                LaunchedEffect(profile) {
                    tempPhone = profile?.data?.phone ?: ""
                    tempEmail = profile?.data?.email ?: ""
                    tempAddress =
                        "${profile?.data?.state ?: ""} ${profile?.data?.address ?: ""} ${profile?.data?.pincode ?: ""}"
                }
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
                    ProfileItem(
                        icon = Icons.Default.Call,
                        label = "Phone Number",
                        value = (profile?.data?.phone ?: "+91XXXXXXXXXX").toString(),
                        onValueChange = {
                            tempPhone = it
                            updateButtonEnabled = true
                        },
                        onEditClick = {
                            editingField =
                                if (editingField == EditableField.PHONE) EditableField.NONE else EditableField.PHONE
                        }

                    )
                    ProfileItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = (profile?.data?.email ?: "example@gmail.com"),
                        onValueChange = {
                            tempEmail = it
                            updateButtonEnabled = true
                        },
                        onEditClick = {
                            editingField =
                                if (editingField == EditableField.EMAIL) EditableField.NONE else EditableField.EMAIL
                        }
                    )
                    ProfileItem(
                        icon = Icons.Default.Info,
                        label = "Address",
                        value = ((profile?.data?.state + " " + profile?.data?.address + " " + profile?.data?.pincode)),
                        onValueChange = {
                            tempAddress = it
                            updateButtonEnabled = true
                        },
                        onEditClick = {
                            editingField =
                                if (editingField == EditableField.ADDRESS) EditableField.NONE else EditableField.ADDRESS
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            val updateInfo = UserInfoUpdate(
                                phone = tempPhone,
                                email = tempEmail,
                                address = tempAddress
                            )
                            viewModel.updateUserProfile(updateInfo)
                            updateButtonEnabled = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SkyBlue,
                            contentColor = Color.White
                        ),
                        enabled = updateButtonEnabled
                    ) {
                        Text(text = "Update Info")
                    }
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
                    ProfileItem(
                        icon = Icons.Default.Call,
                        label = "Phone Number",
                        value = "+91XXXXXXXXXX",
                        onValueChange = {
                            tempPhone = it
                            updateButtonEnabled = true
                        },
                        onEditClick = {
                            editingField =
                                if (editingField == EditableField.PHONE) EditableField.NONE else EditableField.PHONE
                        }
                    )
                    ProfileItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = "example@gmail.com",
                        onValueChange = {
                            tempEmail = it
                            updateButtonEnabled = true
                        },
                        onEditClick = {
                            editingField =
                                if (editingField == EditableField.EMAIL) EditableField.NONE else EditableField.EMAIL
                        }
                    )

                    ProfileItem(
                        icon = Icons.Default.Info,
                        label = "Address",
                        value = "India",
                        onValueChange = {
                            tempAddress = it;
                            updateButtonEnabled = true
                        },
                        onEditClick = {
                            editingField =
                                if (editingField == EditableField.ADDRESS) EditableField.NONE else EditableField.ADDRESS
                        }
                    )
                    Button(
                        onClick = {
                            val updateInfo = UserInfoUpdate(
                                phone = tempPhone,
                                email = tempEmail,
                                address = tempAddress
                            )
                            viewModel.updateUserProfile(updateInfo)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Update Info")
                    }

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
            painter = imagePainter,
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
            text = "@${name.toLowerCase()}",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileItem(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onEditClick: () -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else SkyBlue
    var editingValue by remember { mutableStateOf(value) }
    var isEditing by remember { mutableStateOf(false) }
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
            if (isEditing) {
                TextField(
                    value = editingValue,
                    onValueChange = {
                        editingValue = it
                        onValueChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            } else {
                Text(
                    text = value,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = uiColor,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onEditClick()
                    isEditing = !isEditing
                }
        )
    }
}



