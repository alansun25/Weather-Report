package com.example.weatherreport.network

import com.example.weatherreport.data.CityWeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherAPI {
    @GET("/data/2.5/weather")
    fun getCityWeather(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Call<CityWeatherResult>
}