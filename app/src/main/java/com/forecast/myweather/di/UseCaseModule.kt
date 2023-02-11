package com.forecast.myweather.di

import com.forecast.domain.repo.MyWeatherRepo
import com.forecast.domain.usecase.GetCurrentWeather
import com.forecast.domain.usecase.GetForecast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetWeatherUseCase(mealsRepo: MyWeatherRepo): GetCurrentWeather {
        return GetCurrentWeather(mealsRepo)
    }

    @Provides
    fun provideGetForecastUseCase(mealsRepo: MyWeatherRepo): GetForecast {
        return GetForecast(mealsRepo)
    }
}