package com.forecast.myweather.forecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import com.forecast.domain.usecase.GetForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val getForecast: GetForecast) : ViewModel() {

    private val _currentForecast: MutableStateFlow<ForecastResponse?> = MutableStateFlow(null)
    val currentForecast: StateFlow<ForecastResponse?> = _currentForecast

    fun getForecastData(query: String, units: String, type: String) {
        viewModelScope.launch {
            try {
                if (type.equals("zip")) {
                    _currentForecast.value = getForecast.getForecastZip(
                        query,
                        units,
                        "e3f8facecf0ef485379dd7f8cdafeeda"
                    )
                } else if (type.equals("city")) {
                    _currentForecast.value = getForecast.getForecastCity(
                        query,
                        units,
                        "e3f8facecf0ef485379dd7f8cdafeeda"
                    )
                } else {
                    _currentForecast.value = getForecast.getForecastLatLon(
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