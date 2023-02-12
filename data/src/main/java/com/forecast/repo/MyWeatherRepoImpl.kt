package com.forecast.repo

import com.forecast.data.remote.MyWeatherAPIService
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import com.forecast.domain.repo.MyWeatherRepo

class MyWeatherRepoImpl(private val myWeatherAPIService: MyWeatherAPIService) : MyWeatherRepo {
    override suspend fun getCurrentWeatherByCity(
        city: String,
        units: String,
        appKey: String
    ): CurrentWeatherResponse = myWeatherAPIService.getCurrentWeatherByCity(city, units, appKey)

    override suspend fun getCurrentWeatherByZip(
        zip: String,
        units: String,
        appKey: String
    ): CurrentWeatherResponse = myWeatherAPIService.getCurrentWeatherByZip(zip, units, appKey)

    override suspend fun getCurrentWeatherByLatLon(
        lat: String,
        lon: String,
        units: String,
        appkey: String
    ): CurrentWeatherResponse =
        myWeatherAPIService.getCurrentWeatherByLatLon(lat, lon, units, appkey)

    override suspend fun getForecastCity(
        city: String,
        units: String,
        appKey: String
    ): ForecastResponse =
        myWeatherAPIService.getForecastByCity(city, units, appKey)

    override suspend fun getForecastZip(
        zip: String,
        units: String,
        appKey: String
    ): ForecastResponse =
        myWeatherAPIService.getForecastByZip(zip, units, appKey)

    override suspend fun getForecastLatLon(
        lat: String,
        lon: String,
        units: String,
        appkey: String
    ): ForecastResponse = myWeatherAPIService.getForecastByLatLon(lat, lon, units, appkey)
}