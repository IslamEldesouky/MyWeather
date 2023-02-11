package com.forecast.domain.repo

import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse

interface MyWeatherRepo {
    suspend fun getCurrentWeather(): CurrentWeatherResponse
    suspend fun getForecast(): ForecastResponse
}