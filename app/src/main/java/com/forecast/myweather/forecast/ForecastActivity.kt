package com.forecast.myweather.currentweather

import CurrentWeatherAdapter
import ForecastAdapter
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.forecast.domain.entity.SearchItem
import com.forecast.myweather.R
import com.forecast.myweather.Utils
import com.forecast.myweather.dashboard.DashboardActivity
import com.forecast.myweather.databinding.ActivityForecastBinding
import com.forecast.myweather.forecast.ForecastHandler
import com.forecast.myweather.forecast.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity(), CurrentWeatherAdapter.ItemSelected,
    ForecastHandler {

    private val viewModel: ForecastViewModel by viewModels()
    lateinit var binding: ActivityForecastBinding
    private val searchItems: ArrayList<SearchItem> = ArrayList()
    private lateinit var mPref: SharedPreferences
    private val locationPermissionCode = 2
    private var locationManager: LocationManager? = null
    private val PREF_KEY_FILTER = "filter"
    private val PREF_KEY_FILTER_TEMP = "temp"
    private val PREF_KEY_LAST_SEARCHED = "last"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast)
        binding.setLifecycleOwner(this)
        binding.handler = this
        val forecastAdapter = ForecastAdapter(getFilterTemp())
        val currentWeatherAdapter = CurrentWeatherAdapter(this)
        val rvSearchedItems: RecyclerView = binding.rvSearchItems
        val rvForecasteItems: RecyclerView = binding.rvForecastItems
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        mPref = getSharedPreferences("main", Context.MODE_PRIVATE)
        viewModel.getForecastData(getLastSearched(), getFilterTemp(), "city")
        binding.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.rbZip.isChecked) {
                    viewModel.getForecastData(
                        binding.search.text.toString(),
                        getFilterTemp(),
                        "zip"
                    )
                } else if (binding.rbCity.isChecked) {
                    viewModel.getForecastData(
                        binding.search.text.toString(),
                        getFilterTemp(),
                        "city"
                    )
                } else if (binding.rbLat.isChecked) {
                    viewModel.getForecastData(
                        binding.search.text.toString(),
                        getFilterTemp(),
                        "lat"
                    )
                } else {
                    Toast.makeText(
                        this@ForecastActivity,
                        "Please select a filter",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            } else {
                false
            }
        }
    lifecycleScope.launch {
        viewModel.currentForecast.collect() {
            if (it != null) {
                binding.progressBar.visibility = View.INVISIBLE
                setLastSearched(it.city.name)
                var tmp = ""
                if (getFilterTemp().equals("metric"))
                    tmp = it.list.get(0).main.temp.toString() + "°C"
                else
                    tmp = it.list.get(0).main.temp.toString() + "°F"
                val searchItem = SearchItem(it.city.id, it.city.name, tmp)
                searchItems.add(searchItem)
                if (searchItems.size > 5) {
                    searchItems.removeFirst()
                }
                if (it.list != null) {
                    forecastAdapter.submitList(Utils().getTodaysForecastItems(it?.list))
                    rvForecasteItems.adapter = forecastAdapter
                }
                currentWeatherAdapter.submitList(searchItems)
                rvSearchedItems.adapter = currentWeatherAdapter
            } else {
//                    binding.tvCity.text = getString(R.string.empty_text)
            }
        }
    }
}

override fun itemSelected(searchItem: SearchItem) {
    viewModel.getForecastData(
        searchItem.cityName.toString(),
        getFilterTemp(),
        "city"
    )
}

override fun navigateToHome() {
    val intent = Intent(this@ForecastActivity, DashboardActivity::class.java)
    startActivity(intent)
}

override fun zipCodeSelected() {
    setFilter("zip")
    binding.rbZip.isChecked = true
    binding.rbCurrent.isChecked = false
    binding.rbCity.isChecked = false
    binding.rbLat.isChecked = false
}

override fun citySelected() {
    setFilter("city")
    binding.rbZip.isChecked = false
    binding.rbCurrent.isChecked = false
    binding.rbCity.isChecked = true
    binding.rbLat.isChecked = false
}

override fun latLonSelected() {
    setFilter("lat")
    binding.rbZip.isChecked = false
    binding.rbCurrent.isChecked = false
    binding.rbCity.isChecked = false
    binding.rbLat.isChecked = true
}

override fun currentLocationSelected() {
    setFilter("current")
    getLocation()
    binding.rbZip.isChecked = false
    binding.rbCurrent.isChecked = true
    binding.rbCity.isChecked = false
    binding.rbLat.isChecked = false
}

fun setFilter(filter: String) {
    if (this::mPref.isInitialized)
        mPref.edit().putString(PREF_KEY_FILTER, filter).apply()
}

fun getFilter(filter: String): String {
    if (this::mPref.isInitialized)
        return readFromPreferences(PREF_KEY_FILTER, "").toString()
    else
        return ""
}

fun setFilterTemp(filter: String) {
    if (this::mPref.isInitialized)
        mPref.edit().putString(PREF_KEY_FILTER_TEMP, filter).apply()
}

fun getFilterTemp(): String {
    if (this::mPref.isInitialized)
        return readFromPreferences(PREF_KEY_FILTER_TEMP, "").toString()
    else
        return ""
}

fun setLastSearched(lastSearched: String) {
    if (this::mPref.isInitialized)
        mPref.edit().putString(PREF_KEY_LAST_SEARCHED, lastSearched).apply()
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

private fun getLocation() {
    locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if ((ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }
    locationManager?.requestLocationUpdates(
        LocationManager.NETWORK_PROVIDER,
        0L,
        0f,
        locationListener
    )

}

override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == locationPermissionCode) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}

private val locationListener: LocationListener = object : LocationListener {
    override fun onLocationChanged(location: Location) {
        viewModel.getForecastData(
            location.latitude.toString() + ',' + location.longitude,
            getFilterTemp(),
            "lat"
        )
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}
}