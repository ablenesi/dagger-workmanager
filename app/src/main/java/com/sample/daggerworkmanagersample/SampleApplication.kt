package com.sample.daggerworkmanagersample

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.sample.daggerworkmanagersample.di.DaggerInjection
import com.sample.daggerworkmanagersample.di.Injection
import com.sample.daggerworkmanagersample.network.OPEN_WEATHER_BASE_URL
import com.sample.daggerworkmanagersample.work.DaggerWorkerFactory

class SampleApplication : Application(), Injection by DaggerInjection() {
    override fun onCreate() {
        super.onCreate()
        initComponent(this, OPEN_WEATHER_BASE_URL)
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(DaggerWorkerFactory()).build())
    }
}