package com.zip_feast.utils.apputils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.zip_feast.presentation.theme.CompactDimens
import com.zip_feast.presentation.theme.Dimens

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


sealed class Resource<T>(val data: T?=null, val errorMessage: String?=null){
    class Loading<T>: Resource<T>()
    class Success<T>(data: T): Resource<T>(data =data)
    class Error<T>(errorMessage: String): Resource<T>(errorMessage =errorMessage)
}