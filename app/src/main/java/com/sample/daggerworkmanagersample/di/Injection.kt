package com.sample.daggerworkmanagersample.di

import android.app.Activity
import androidx.work.ListenableWorker
import com.sample.daggerworkmanagersample.SampleApplication
import com.sample.daggerworkmanagersample.work.HasWorkerInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Besides defining the needed injectors for dagger, it also defines the actions that can be taken with the DI framework such as initializing and reinitializing.
 */
interface Injection : HasActivityInjector, HasWorkerInjector {

    /**
     * Initializes the DI framework. It has to be called in order to inject or inject into any other class.
     *
     * It injects the fields into the given [application] class as well.
     */
    fun initComponent(application: SampleApplication, baseUrl: String)

    /**
     * Re-initializes the component, meaning the current dependencies created will be released and a new one will be created instead.*
     */
    fun reinitCompnent(baseUrl: String)

}

class DaggerInjection : Injection {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var workerInjector: DispatchingAndroidInjector<ListenableWorker>

    private var application: SampleApplication? = null

    override fun initComponent(application: SampleApplication, baseUrl: String) {
        this.application = application
        DaggerAppComponent.builder()
            .baseUrl(baseUrl).apply { seedInstance(application) }
            .build().also {
                it.inject(this)
                it.inject(application)
            }
    }

    override fun reinitCompnent(baseUrl: String) { initComponent(application!!, baseUrl) }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
    override fun workerInjector(): AndroidInjector<ListenableWorker> = workerInjector
}
