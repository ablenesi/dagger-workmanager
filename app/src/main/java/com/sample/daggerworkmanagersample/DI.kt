package com.sample.daggerworkmanagersample

import android.app.Activity
import androidx.work.ListenableWorker
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Qualifier
import kotlin.reflect.KClass

@Component(
    modules = [
        SampleAssistedInjectModule::class,
        WorkerBindingModule::class,
        NetworkModule::class,
        ActivityBinderModule::class
    ]
)
interface SampleComponent : AndroidInjector<SampleApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SampleApplication>() {
        @BindsInstance
        abstract fun baseUrl(@BaseUrl baseUrl: String): Builder
    }

    @BaseUrl
    fun baseUrl(): String

    fun factory(): SampleWorkerFactory

    fun inject(daggerInjection: Injection.DaggerInjection)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

}

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


interface WeatherService {
    @GET("weather")
    fun weather(@Query("q") query: String = "London"): Call<String>
}

@Qualifier
annotation class BaseUrl

const val OTHER_BASE_URL = "https://my-json-server.typicode.com/ablenesi/demo/"
const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

const val OPEN_WEATHER_APP_ID = "b6907d289e10d714a6e88b30761fae22"

@Module
abstract class ActivityBinderModule {
    @ContributesAndroidInjector
    abstract fun bindChangeActivity(): ChangeActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}