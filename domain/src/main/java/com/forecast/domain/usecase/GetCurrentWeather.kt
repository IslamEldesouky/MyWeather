package com.forecast.domain.usecase

import com.forecast.domain.repo.MyWeatherRepo

class GetCurrentWeather(private val myWeatherRepo: MyWeatherRepo) {
    suspend fun getCurrentWeatherCity(city: String,appkey:String) = myWeatherRepo.getCurrentWeatherByCity(city,appkey)
    suspend fun getCurrentWeatherZip(zip: String,appkey:String) = myWeatherRepo.getCurrentWeatherByZip(zip,appkey)
    suspend fun getCurrentWeatherLatLon(lat:String, lon :String,appkey:String) = myWeatherRepo.getCurrentWeatherByLatLon(lat,lon,appkey)
}