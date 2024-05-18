package com.zip_feast.ui.auth.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.zip_feast.R
import com.zip_feast.ui.theme.focusTextFieldText
import com.zip_feast.ui.theme.textFieldContainer
import com.zip_feast.ui.theme.unfocusTextFieldText

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    TextField(
        modifier = modifier.padding(start = 15.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = uiColor
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        value = value,
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = MaterialTheme.colorScheme.unfocusTextFieldText,
            focusedIndicatorColor = MaterialTheme.colorScheme.focusTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
        ),
        onValueChange = {onValueChange(it)}
    )

}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.visible_icon)
    } else {
        painterResource(id = R.drawable.visibility_off_icon)
    }
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    TextField(
        modifier = modifier.padding(start = 15.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = uiColor
            )
        },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility }
            ) {
                Icon(painter = icon, contentDescription = "Toggle visibility")
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        value = value,
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = MaterialTheme.colorScheme.unfocusTextFieldText,
            focusedIndicatorColor = MaterialTheme.colorScheme.focusTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
        ),
        onValueChange = {onValueChange(it)}
    )

}