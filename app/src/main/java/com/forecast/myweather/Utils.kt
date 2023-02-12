package com.forecast.myweather

import android.os.Build
import androidx.annotation.RequiresApi
import com.forecast.domain.entity.CurrentWeatherResponse
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun compareDay(date: String): Boolean {
        val c = Calendar.getInstance()
        val odt: LocalDate = LocalDate.parse(
            date.split(" ")[0], DateTimeFormatter.ofPattern("yyy-MM-dd")
        )
        return c.get(Calendar.DAY_OF_MONTH) == odt.dayOfMonth
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodaysForecastItems(
        list: List<CurrentWeatherResponse>
    ): List<CurrentWeatherResponse> {
        val resultList: ArrayList<CurrentWeatherResponse> = ArrayList()
        for (item in list) {
            if (compareDay(item.dt_txt)) {
                resultList.add(item)
            }
        }
        return resultList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time : String) : String {
        val dateToFormat = LocalDateTime.parse(time)
        val dateFormatExpression = SimpleDateFormat("hh:mm a")
        val formattedDate: String = dateFormatExpression.format(dateToFormat)
        return formattedDate
    }
}