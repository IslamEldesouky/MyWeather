package com.forecast.data.remote

import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyWeatherAPIService {

    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): CurrentWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByZip(
        @Query("zip") zip: String,
        @Query("appid") apiKey: String
    ): CurrentWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): CurrentWeatherResponse

    @GET("forecast")
    suspend fun getForecastByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): ForecastResponse

    @GET("forecast")
    suspend fun getForecastByZip(
        @Query("zip") zip: String,
        @Query("appid") apiKey: String
    ): ForecastResponse

    @GET("forecast")
    suspend fun getForecastByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): ForecastResponse
}