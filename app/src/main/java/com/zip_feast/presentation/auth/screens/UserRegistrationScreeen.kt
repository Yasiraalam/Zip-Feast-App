package com.zip_feast.presentation.auth.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zip_feast.R
import com.zip_feast.data.remote.models.loginModel.UserRequest
import com.zip_feast.presentation.theme.BlueGray
import com.zip_feast.presentation.theme.Roboto
import com.zip_feast.presentation.auth.authnavigation.Screen
import com.zip_feast.presentation.auth.authviewmodels.AuthViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource


@Composable
fun RegistrationScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(13.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopRegSection()
            Spacer(modifier = Modifier.height(6.dp))
            RegistrationSection(authViewModel, navController)
        }
    }
}

@Composable
fun TopRegSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(alignment = Alignment.BottomCenter),
            text = stringResource(id = R.string.registration),
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )
    }
}

@Composable
private fun RegistrationSection(authViewModel: AuthViewModel, navController: NavController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    var emailError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") }
    var confirmPasswordError by rememberSaveable { mutableStateOf("") }

    val signUpState_message by authViewModel.signUpState.observeAsState()
    var isLoading by remember { mutableStateOf(false) }

    // Check if all fields are filled
    val allFieldsFilled = username.isNotBlank() && emailError.isBlank() &&
            passwordError.isBlank() && confirmPasswordError.isBlank()

    LaunchedEffect(signUpState_message) {
        signUpState_message?.let {
            when (it) {
                is Resource.Error -> {
                    isLoading = false
                    emailError = it.errorMessage ?: "An error occurred"
                }
                is Resource.Success -> {
                    isLoading = false
                    emailError = ""
                }
                is Resource.Loading -> {
                    isLoading = true
                    emailError = ""
                }
            }
        }
    }
    OutlinedTextField(
        value = username,
        onValueChange = { username = it },
        label = { Text("Username") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
    )
    Spacer(modifier = Modifier.height(8.dp))


    OutlinedTextField(
        value = email,
        onValueChange = {
            email = it
            emailError =
                if (email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|yahoo|outlook)\\.com$".toRegex())) {
                    ""
                } else {
                    "Invalid email address"
                }
        },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = null
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
    // here we show error email
    if (emailError.isNotEmpty()) {
        Text(
            text = emailError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            passwordError = when {
                password.length < 8 -> "Password must be at least 8 characters"
                !password.contains("[A-Z]".toRegex()) -> "Password must contain at least one uppercase letter"
                !password.contains("[^a-zA-Z0-9]".toRegex()) -> "Password must contain at least one special character"
                else -> ""
            }
        },
        label = { Text("Password") },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                val icon = if (passwordVisibility) {
                    painterResource(id = R.drawable.visible_icon)
                } else {
                    painterResource(id = R.drawable.visibility_off_icon)
                }
                Icon(painter = icon, contentDescription = "Toggle visibility")
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )
    )
    //password error is here
    if (passwordError.isNotEmpty()) {
        Text(
            text = passwordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = {
            confirmPassword = it
            confirmPasswordError = if (confirmPassword == password) {
                ""
            } else {
                "Password does not match"
            }
        },
        label = { Text("Confirm Password") },
        visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                val icon = if (confirmPasswordVisibility) {
                    painterResource(id = R.drawable.visible_icon)
                } else {
                    painterResource(id = R.drawable.visibility_off_icon)
                }
                Icon(painter = icon, contentDescription = "Toggle visibility")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )
    //conform password error showing here
    if (confirmPasswordError.isNotEmpty()) {
        Text(
            text = confirmPasswordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    val context = LocalContext.current
    Button(
        onClick = {
            if (emailError.isEmpty() && passwordError.isEmpty() && confirmPasswordError.isEmpty()) {
                isLoading = true
                authViewModel.registerUser(
                    userRequest = UserRequest(
                        name = username,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword,
                        null
                    )
                ){
                    isLoading = false
                    if (signUpState_message is Resource.Success) {
                        navController.navigate(Screen.Login.route)
                    }
                }
            }else{
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        },
        enabled = !isLoading,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else SkyBlue,
            contentColor = Color.White
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = if (isSystemInDarkTheme()) Color.Black else Color.Blue,
                strokeWidth = 2.dp
            )
        }else {
            Text(text = "Register")
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    alreadyHaveAccount(navController)
}

@Composable
fun alreadyHaveAccount(navController: NavController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFF94A3B8),
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Already have an account? ")
        }
        pushStringAnnotation(tag = "Login", annotation = "Login")
        withStyle(
            style = SpanStyle(
                color = uiColor,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Login")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.4f)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "Login", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
            }
        )
    }
}



