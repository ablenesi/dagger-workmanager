package com.sample.daggerworkmanagersample.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sample.daggerworkmanagersample.network.WeatherService
import javax.inject.Inject

class HelloWorldWorker constructor(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {

    @Inject
    lateinit var weatherService: WeatherService
    @Inject
    lateinit var baseUrl: String

    private val TAG = "HelloWorldWorker"
    override fun doWork(): Result {
        Log.d(TAG, "Hello world!")
        Log.d(TAG, "Injected service: $weatherService")
        Log.d(TAG, "Injected Base url: $baseUrl")

        // TODO: Execute request

        return Result.success()
    }
}
