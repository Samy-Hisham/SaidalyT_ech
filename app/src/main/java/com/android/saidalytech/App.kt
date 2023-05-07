package com.android.saidalytech

import android.app.Application
import com.android.saidalytech.uitls.MySharedPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)
    }
}