package com.zip_feast.presentation.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zip_feast.R
import com.zip_feast.data.remote.models.LoginModel
import com.zip_feast.presentation.theme.Roboto
import com.zip_feast.presentation.theme.dimens
import com.zip_feast.presentation.auth.authnavigation.Screen
import com.zip_feast.presentation.auth.authviewmodels.AuthViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    onClick:()-> Unit
) {
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSection()
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                LoginSection(authViewModel = authViewModel, onClick = onClick)
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
                SocialMediaSection()
                CreateAccountSection(navController)
            }
        }
    }
}

@Composable
fun TopSection(
    label: String
) {

    val uiColor = if(isSystemInDarkTheme()) Color.White else Color.Black

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.46f),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.logoSize),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                tint = uiColor
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column{
                Text(
                    text = stringResource(id = R.string.zipfeast),
                    style = MaterialTheme.typography.headlineMedium,
                    color = uiColor
                )
                Text(
                    text = stringResource(id = R.string.zipfeast_dec),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor
                )

            }
        }
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(alignment = Alignment.BottomCenter),
            text = label,
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )

    }

}

@Composable
private fun CreateAccountSection(navController: NavController) {
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
            append("Don't have an account? ")
        }
        pushStringAnnotation(tag = "create_account", annotation = "create_account")
        withStyle(
            style = SpanStyle(
                color = uiColor,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Create now")
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
                annotatedText.getStringAnnotations(tag = "create_account", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate(Screen.Register.route)
                    }
            }
        )
    }
}

@Composable
private fun SocialMediaSection() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Or continue with",
            style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF64749B))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SocialMediaLogin(icon = R.drawable.google_icon, text = "Google") {

            }
        }
    }
}

@Composable
private fun LoginSection(onClick: () -> Unit, authViewModel: AuthViewModel) {
    val ApiResponseMessage by authViewModel.message.observeAsState()
    var isLoading by rememberSaveable{ mutableStateOf(false) }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") }
    EmailTextField(
        label = "Email",
        leadingIcon = Icons.Default.Email,
        value = email,
        onValueChange = {
            email = it
            emailError =
                if (email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|yahoo|outlook)\\.com$".toRegex())) {
                    ""
                }
                else {
                    "Invalid email address"
                }
        })
    if (emailError.isNotEmpty()) {
        Text(
            text = emailError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
    PasswordTextField(
        label = "Password",
        value = password,
        leadingIcon = Icons.Default.Lock,
        onValueChange = {
        password = it
        passwordError = when {
            password.length < 8 -> "Password must be at least 8 characters"
            !password.contains("[A-Z]".toRegex()) -> "Password must contain at least one uppercase letter"
            !password.contains("[^a-zA-Z0-9]".toRegex()) -> "Password must contain at least one special character"
            else -> ""
        }
    })
    if (passwordError.isNotEmpty()) {
        Text(
            text = passwordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight),
        onClick = {
            if (emailError.isEmpty() && passwordError.isEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                isLoading = true
                 authViewModel.loginUser(
                     LoginModel(
                         email = email,
                         password = password
                     )
                 ){
                     isLoading = false
                     if(authViewModel.getToken() != null){
                         onClick()
                     }
                 }
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = if (isSystemInDarkTheme()) Color.Black else Color.Blue,
                strokeWidth = 2.dp
            )
        }else {
            Text(
                fontSize = 12.sp,
                text = stringResource(id = R.string.loginIn),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
fun TopSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.40f),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.logoSize),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                tint = uiColor
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.zipfeast),
                    style = MaterialTheme.typography.headlineMedium,
                    color = uiColor
                )
                Text(
                    text = stringResource(id = R.string.zipfeast_dec),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor
                )

            }
        }
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(alignment = Alignment.BottomCenter),
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )

    }

}


