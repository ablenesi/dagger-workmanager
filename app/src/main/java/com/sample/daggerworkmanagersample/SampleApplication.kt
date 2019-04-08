package com.sample.daggerworkmanagersample

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import javax.inject.Inject

class SampleApplication : Application(), Injection by Injection.DaggerInjection() {

    override fun onCreate() {
        super.onCreate()
        initComponent(this, OPEN_WEATHER_BASE_URL)
    }
}