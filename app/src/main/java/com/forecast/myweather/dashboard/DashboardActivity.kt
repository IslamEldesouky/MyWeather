package com.forecast.myweather.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.forecast.myweather.R
import com.forecast.myweather.currentweather.CurrentWeatherActivity
import com.forecast.myweather.currentweather.ForecastActivity
import com.forecast.myweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), DashboardHandler {

    private val viewModel: DashboardViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    private lateinit var mPref: SharedPreferences
    private val PREF_KEY_FILTER = "filter"
    private val PREF_KEY_FILTER_TEMP = "temp"
    private val PREF_KEY_LAST_SEARCHED = "last"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = this
        mPref = getSharedPreferences("main", Context.MODE_PRIVATE)
        viewModel.getCurrentWeatherData(
            getFilterTemp(),
            getLastSearched()
        )

        lifecycleScope.launch {
            viewModel.currentWeather.collect() {
                if (it != null) {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvCity.text = it.name
                    binding.tvTmp.text = buildString {
                        append(it.main.temp.toString())
                        if (getFilterTemp().equals("metric"))
                            append("°C")
                        else
                            append("°F")
                    }
                    binding.tvTmpDescription.text = it.weather.get(0).description
                } else {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvCity.text = getString(R.string.empty_text)
                }
            }
        }
    }

    override fun navigateToCurrentWeather() {
        val intent = Intent(this, CurrentWeatherActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToForecast() {
        if (getLastSearched().equals("")) {
            Toast.makeText(this@DashboardActivity, "Please search a location", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this@DashboardActivity, ForecastActivity::class.java)
            startActivity(intent)
        }
    }

    fun getFilterTemp(): String {
        if (this::mPref.isInitialized)
            return readFromPreferences(PREF_KEY_FILTER_TEMP, "").toString()
        else
            return ""
    }

    fun getLastSearched(): String {
        if (this::mPref.isInitialized)
            return readFromPreferences(PREF_KEY_LAST_SEARCHED, "").toString()
        else
            return ""
    }

    private fun readFromPreferences(
        preferenceKey: String,
        defaultValue: String
    ): String? {
        if (this::mPref.isInitialized) {
            return mPref.getString(preferenceKey, defaultValue)
        } else {
            return ""
        }
    }
}