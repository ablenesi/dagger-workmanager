package com.sample.daggerworkmanagersample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun weather(@Query("q") query: String = "London", @Query("api") apiKey: String = OPEN_WEATHER_APP_ID): Call<String>
}

const val OTHER_BASE_URL = "https://my-json-server.typicode.com/ablenesi/demo/"
const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

const val OPEN_WEATHER_APP_ID = "b6907d289e10d714a6e88b30761fae22"