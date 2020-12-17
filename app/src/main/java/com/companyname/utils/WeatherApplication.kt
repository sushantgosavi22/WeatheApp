package com.companyname.utils

import android.app.Application
import android.content.Context

class WeatherApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object{
        lateinit var app : WeatherApplication
        fun getContext(): Context? {
            return app
        }
    }
}