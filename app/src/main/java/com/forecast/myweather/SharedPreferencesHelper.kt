package com.forecast.myweather

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private lateinit var mPref: SharedPreferences
    private val PREF_KEY_FILTER = "filter"
    private val PREF_KEY_FILTER_TEMP = "temp"
    private val PREF_KEY_LAST_SEARCHED = "last"


    fun create(context: Context): SharedPreferencesHelper {
        if (context != null) {
            mPref = context.getSharedPreferences("main", Context.MODE_PRIVATE)
        }
        return SharedPreferencesHelper(context)
    }

    private fun saveToPreferences(
        preferenceKey: String,
        preferenceValue: String
    ) {
        if (this::mPref.isInitialized)
            mPref.edit().putString(preferenceKey, preferenceValue).apply()
    }

    private fun saveToPreferences(
        preferenceKey: String,
        preferenceValue: Boolean
    ) {
        mPref.edit().putBoolean(preferenceKey, preferenceValue).apply()
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

    private fun readFromPreferences(
        preferenceKey: String,
        defaultValue: Boolean
    ): Boolean {
        return mPref.getBoolean(preferenceKey, defaultValue)
    }

    fun clear(mPref: SharedPreferences) {
        mPref.edit().clear().apply()
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
}