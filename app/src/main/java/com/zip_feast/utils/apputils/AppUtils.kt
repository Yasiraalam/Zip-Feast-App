package com.zip_feast.utils.apputils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.zip_feast.ui.theme.CompactDimens
import com.zip_feast.ui.theme.Dimens

@Composable
fun AppDimens(
    appDimens:Dimens,
    content: @Composable () -> Unit
) {
    val appDimens = remember {
        appDimens
    }
    CompositionLocalProvider(LocalAppDimens provides appDimens) {
        content()
    }
    
}
val LocalAppDimens = compositionLocalOf {
    CompactDimens
}