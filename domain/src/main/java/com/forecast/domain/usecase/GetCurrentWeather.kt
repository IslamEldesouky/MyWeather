package com.forecast.domain.usecase

import com.forecast.domain.repo.MyWeatherRepo

class GetCurrentWeather(private val myWeatherRepo: MyWeatherRepo) {
    suspend fun getCurrentWeatherCity(city: String, units: String, appkey: String) =
        myWeatherRepo.getCurrentWeatherByCity(city, units, appkey)

    suspend fun getCurrentWeatherZip(zip: String, units: String, appkey: String) =
        myWeatherRepo.getCurrentWeatherByZip(zip, units, appkey)

    suspend fun getCurrentWeatherLatLon(lat: String, lon: String, units: String, appkey: String) =
        myWeatherRepo.getCurrentWeatherByLatLon(lat, lon, units, appkey)
}