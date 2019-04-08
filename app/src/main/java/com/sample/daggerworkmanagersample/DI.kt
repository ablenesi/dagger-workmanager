package com.sample.daggerworkmanagersample

import android.app.Activity
import androidx.work.ListenableWorker
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Qualifier
import kotlin.reflect.KClass

@Component(
    modules = [
        SampleAssistedInjectModule::class,
        WorkerBindingModule::class,
        NetworkModule::class,
        ActivityBinderModule::class,
        AppModule::class
    ]
)
interface SampleComponent : AndroidInjector<SampleApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SampleApplication>() {
        @BindsInstance
        abstract fun baseUrl(@BaseUrl baseUrl: String): Builder
        abstract fun appModule(appModule: AppModule): Builder
        abstract override fun build(): SampleComponent
    }

    fun factory(): SampleWorkerFactory

    fun inject(daggerInjection: Injection.DaggerInjection)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    @BaseUrl
    fun baseUrl(): String

}

// region Work Manager
@Module(includes = [AssistedInject_SampleAssistedInjectModule::class])
@AssistedModule
interface SampleAssistedInjectModule

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(HelloWorldWorker::class)
    fun bindHelloWorldWorker(factory: HelloWorldWorker.Factory): ChildWorkerFactory
}
// endregion

// region Networking
@Module
class NetworkModule {
    @Provides
    fun provideRetrofit(@BaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Reusable
    fun createWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}

@Qualifier
annotation class BaseUrl
// endregion

// region Activity
@Module
interface ActivityBinderModule {
    @ContributesAndroidInjector
    fun bindChangeActivity(): ChangeActivity
}
// endregion

// region Context
/**
 * Module for application dependencies which require a {@link Context}
 */
@Module
class AppModule{
    @Provides
    fun provideInjection(app: SampleApplication): Injection = app
}
// endregion