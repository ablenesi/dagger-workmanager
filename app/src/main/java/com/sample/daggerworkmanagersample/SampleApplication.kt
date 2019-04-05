package com.sample.daggerworkmanagersample

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import javax.inject.Inject

class SampleApplication : Application(), Injection by Injection.DaggerInjection() {

    @Inject
    lateinit var factory: SampleWorkerFactory

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())
    }
}