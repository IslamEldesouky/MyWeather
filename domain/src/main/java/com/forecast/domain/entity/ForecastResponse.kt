package com.forecast.domain.entity

data class ForecastResponse(
    val list: List<CurrentWeatherResponse>,
    val city: City
    )
data class City(
    val id:Int,
    val name : String,
)
