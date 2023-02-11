package com.forecast.domain.usecase

import com.forecast.domain.repo.MyWeatherRepo

class GetForecast(private val myWeatherRepo: MyWeatherRepo) {
    suspend operator fun invoke() = myWeatherRepo.getForecast()
}