package com.forecast.myweather.forecast

interface ForecastHandler {
    fun navigateToHome()

    fun zipCodeSelected()

    fun citySelected()

    fun latLonSelected()

    fun currentLocationSelected()
}