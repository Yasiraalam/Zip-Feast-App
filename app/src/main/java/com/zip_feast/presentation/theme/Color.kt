package com.zip_feast.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val Black = Color(0xFF000113)
val LightBlueWhite = Color(0xFFF1F5F9)
val BlueGray = Color(0xFFF334155)

val ColorScheme.focusTextFieldText
      @Composable
  get() = if (isSystemInDarkTheme()) Color.White else Color.Black

val ColorScheme.unfocusTextFieldText
      @Composable
  get() = if (isSystemInDarkTheme()) Color(0xFF94A3BB) else Color(0xFF475569)

val ColorScheme.textFieldContainer
    @Composable
    get() = if (isSystemInDarkTheme()) BlueGray.copy(alpha = 0.6f) else LightBlueWhite