package com.forecast.myweather.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import com.forecast.domain.usecase.GetCurrentWeather
import com.forecast.domain.usecase.GetForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val getForecast: GetForecast) : ViewModel() {

    private val _currentWeather : MutableStateFlow<ForecastResponse?> = MutableStateFlow(null)
    val currentWeather : StateFlow<ForecastResponse?>  = _currentWeather

    fun getCurrentWeatherData(){
        viewModelScope.launch{
            try {
                _currentWeather.value = getForecast.getForecastCity("Cairo","e3f8facecf0ef485379dd7f8cdafeeda")
            }catch (e:java.lang.Exception){
                Log.e("ERROR",e.message.toString())
            }
        }
    }
}