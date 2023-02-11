package com.forecast.myweather.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.forecast.myweather.R
import com.forecast.myweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(),DashboardHandler {

    private val viewModel: DashboardViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.getCurrentWeatherData()
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            viewModel.currentWeather.collect() {
                binding.progressBar.visibility = View.INVISIBLE
                binding.tvCity.text = it?.name
                binding.tvTmp.text = buildString {
                    append(it?.main?.temp.toString())
                    append("°K")
                }
                binding.tvTmpDescription.text = it?.weather?.get(0)?.description
            }
        }
    }

    override fun navigateToCurrentWeather() {
        TODO("Not yet implemented")
    }

    override fun navigateToForecast() {
        TODO("Not yet implemented")
    }
}