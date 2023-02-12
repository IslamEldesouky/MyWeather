package com.forecast.myweather.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import com.forecast.domain.usecase.GetCurrentWeather
import com.forecast.domain.usecase.GetForecast
import com.forecast.myweather.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val getCurrentWeather: GetCurrentWeather) : ViewModel() {

    private val _currentWeather : MutableStateFlow<CurrentWeatherResponse?> = MutableStateFlow(null)
    val currentWeather : StateFlow<CurrentWeatherResponse?>  = _currentWeather

    fun getCurrentWeatherData(units:String, city:String){
        viewModelScope.launch{
            try {
                _currentWeather.value = getCurrentWeather.getCurrentWeatherCity(city,units,BuildConfig.WEATHER_API_KEY)
            }catch (e:java.lang.Exception){
                Log.e("ERROR",e.message.toString())
            }
        }
    }
}