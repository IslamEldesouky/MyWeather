package com.forecast.domain.usecase

import com.forecast.domain.repo.MyWeatherRepo

class GetForecast(private val myWeatherRepo: MyWeatherRepo) {
    suspend fun getForecastCity(city: String, appkey:String) = myWeatherRepo.getForecastCity(city,appkey)
    suspend fun getForecastZip(zip: String, appkey:String) = myWeatherRepo.getForecastZip(zip,appkey)
    suspend fun getForecastLatLon(lat : String, lon : String, appkey: String) = myWeatherRepo.getForecastLatLon(lat,lon,appkey)
}