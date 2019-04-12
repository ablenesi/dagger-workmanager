package com.sample.daggerworkmanagersample.work

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface WorkerBinderModule {

    @ContributesAndroidInjector
    fun bindHelloWorldWoeker(): HelloWorldWorker
}