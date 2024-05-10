package com.zip_feast.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.zip_feast.ui.theme.focusTextFieldText
import com.zip_feast.ui.theme.textFieldContainer
import com.zip_feast.ui.theme.unfocusTextFieldText

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    trailing: ImageVector,
    value: String,
    onValueChange: (String) -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    TextField(
        modifier = modifier,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = uiColor
            )
        },
        trailingIcon = {
            // TODO: put hide password icon
        },
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