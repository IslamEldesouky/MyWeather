package com.forecast.domain.usecase

import com.forecast.domain.repo.MyWeatherRepo

class GetForecast(private val myWeatherRepo: MyWeatherRepo) {
    suspend fun getForecastCity(city: String,units:String, appkey:String) = myWeatherRepo.getForecastCity(city,units,appkey)
    suspend fun getForecastZip(zip: String,units:String, appkey:String) = myWeatherRepo.getForecastZip(zip,units,appkey)
    suspend fun getForecastLatLon(lat : String, lon : String,units:String, appkey: String) = myWeatherRepo.getForecastLatLon(lat,lon,units,appkey)
}