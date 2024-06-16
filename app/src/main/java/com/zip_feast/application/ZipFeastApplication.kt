package com.zip_feast.application

import android.app.Application
import com.zip_feast.data.local.CartSharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZipFeastApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CartSharedPreferences.init(this)
    }
}
