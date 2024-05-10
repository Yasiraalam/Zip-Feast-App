package com.zip_feast.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.zip_feast.R
import com.zip_feast.ui.theme.Black
import com.zip_feast.ui.theme.Roboto
import com.zip_feast.ui.theme.blueGray
import com.zip_feast.ui.theme.dimens


@Composable
fun LoginScreen() {
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSection()
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                LoginSection()
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
                SocialMediaSection()
                CreateAccountSection()
            }
        }
    }
}

@Composable
private fun CreateAccountSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.4f)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = buildAnnotatedString {
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
private fun LoginSection() {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    LoginTextField(
        label = "Email",
        value = email,
        onValueChange = { email = it },
        trailing = Icons.Default.Face,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
    LoginTextField(
        modifier = Modifier.fillMaxWidth(),
        label = "Password",
        trailing =Icons.Default.Face,
        value = password,
        onValueChange = {password = it}
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight),
        onClick = {
            //TODO:handle login here
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) blueGray else Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.loginIn),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
        )

    }
}

@Composable
fun TopSection() {
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
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )

    }
    
}


