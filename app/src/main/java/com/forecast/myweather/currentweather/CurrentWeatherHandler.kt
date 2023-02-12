package com.forecast.myweather.currentweather

interface CurrentWeatherHandler {

    fun navigateToHome()

    fun navigateToForecast()

    fun zipCodeSelected()

    fun citySelected()

    fun latLonSelected()

    fun currentLocationSelected()

    fun switchToFahrenheit()

    fun switchToCelsius()
}