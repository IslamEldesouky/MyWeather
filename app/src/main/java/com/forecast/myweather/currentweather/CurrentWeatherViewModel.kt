package com.forecast.myweather.currentweather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.usecase.GetCurrentWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(private val getCurrentWeather: GetCurrentWeather) :
    ViewModel() {

    private val _currentWeather: MutableStateFlow<CurrentWeatherResponse?> = MutableStateFlow(null)
    val currentWeather: StateFlow<CurrentWeatherResponse?> = _currentWeather

    fun getCurrentWeatherData(query: String, units: String, type: String) {
        viewModelScope.launch {
            try {
                if (type.equals("zip")) {
                    _currentWeather.value = getCurrentWeather.getCurrentWeatherZip(
                        query,
                        units,
                        "e3f8facecf0ef485379dd7f8cdafeeda"
                    )
                } else if (type.equals("city")) {
                    _currentWeather.value = getCurrentWeather.getCurrentWeatherCity(
                        query,
                        units,
                        "e3f8facecf0ef485379dd7f8cdafeeda"
                    )
                } else {
                    _currentWeather.value = getCurrentWeather.getCurrentWeatherLatLon(
                        query.split(',')[0],
                        query.split(',')[0],
                        units,
                        "e3f8facecf0ef485379dd7f8cdafeeda"
                    )
                }

            } catch (e: java.lang.Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }
}