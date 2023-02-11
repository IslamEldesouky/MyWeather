package com.forecast.domain.usecase

import com.forecast.domain.repo.MyWeatherRepo

class GetCurrentWeather(private val myWeatherRepo: MyWeatherRepo) {
    suspend operator fun invoke() = myWeatherRepo.getCurrentWeather()
}