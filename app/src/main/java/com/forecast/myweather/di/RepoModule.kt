package com.forecast.myweather.di

import com.forecast.data.remote.MyWeatherAPIService
import com.forecast.domain.repo.MyWeatherRepo
import com.forecast.repo.MyWeatherRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: MyWeatherAPIService): MyWeatherRepo {
        return MyWeatherRepoImpl(apiService)
    }
}