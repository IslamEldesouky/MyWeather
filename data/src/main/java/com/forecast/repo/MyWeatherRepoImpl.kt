package com.forecast.repo

import com.forecast.data.remote.MyWeatherAPIService
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import com.forecast.domain.repo.MyWeatherRepo

class MyWeatherRepoImpl(private val myWeatherAPIService: MyWeatherAPIService) : MyWeatherRepo {
    override suspend fun getCurrentWeatherByCity(
        city: String,
        appKey: String
    ): CurrentWeatherResponse = myWeatherAPIService.getCurrentWeatherByCity(city, appKey)

    override suspend fun getCurrentWeatherByZip(
        zip: String,
        appKey: String
    ): CurrentWeatherResponse = myWeatherAPIService.getCurrentWeatherByZip(zip, appKey)

    override suspend fun getCurrentWeatherByLatLon(
        lat: String,
        lon: String,
        appkey: String
    ): CurrentWeatherResponse = myWeatherAPIService.getCurrentWeatherByLatLon(lat, lon, appkey)

    override suspend fun getForecastCity(city: String, appKey: String): ForecastResponse =
        myWeatherAPIService.getForecastByCity(city, appKey)

    override suspend fun getForecastZip(zip: String, appKey: String): ForecastResponse =
        myWeatherAPIService.getForecastByZip(zip, appKey)

    override suspend fun getForecastLatLon(
        lat: String,
        lon: String,
        appkey: String
    ): ForecastResponse = myWeatherAPIService.getForecastByLatLon(lat, lon, appkey)
}