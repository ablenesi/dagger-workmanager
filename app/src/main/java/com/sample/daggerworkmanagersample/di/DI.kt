package com.sample.daggerworkmanagersample.di

import android.app.Activity
import com.sample.daggerworkmanagersample.SampleApplication
import com.sample.daggerworkmanagersample.network.NetworkModule
import com.sample.daggerworkmanagersample.ui.ChangeActivity
import com.sample.daggerworkmanagersample.work.WorkerBinderModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector

@Component(
    modules = [
        AndroidInjectionModule::class,
        WorkerBinderModule::class,
        ActivityBinderModule::class,
        NetworkModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<SampleApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SampleApplication>() {
        @BindsInstance
        abstract fun baseUrl(baseUrl: String): Builder
        abstract fun appModule(appModule: AppModule): Builder
        abstract override fun build(): AppComponent
    }

    fun inject(daggerInjection: DaggerInjection)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    fun baseUrl(): String

}

@Module
class AppModule{
    @Provides
    fun provideInjection(app: SampleApplication): Injection = app
}

@Module
interface ActivityBinderModule {
    @ContributesAndroidInjector
    fun bindChangeActivity(): ChangeActivity
}
