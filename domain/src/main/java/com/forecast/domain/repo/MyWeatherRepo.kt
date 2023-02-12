package com.forecast.domain.repo

import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse

interface MyWeatherRepo {
    suspend fun getCurrentWeatherByCity(
        city: String,
        units: String,
        appKey: String
    ): CurrentWeatherResponse

    suspend fun getCurrentWeatherByZip(
        zip: String,
        units: String,
        appKey: String
    ): CurrentWeatherResponse

    suspend fun getCurrentWeatherByLatLon(
        lat: String,
        lon: String,
        units: String,
        appkey: String
    ): CurrentWeatherResponse

    suspend fun getForecastCity(city: String, units: String, appKey: String): ForecastResponse
    suspend fun getForecastZip(zip: String, units: String, appKey: String): ForecastResponse
    suspend fun getForecastLatLon(
        lat: String,
        lon: String,
        units: String,
        appkey: String
    ): ForecastResponse
}