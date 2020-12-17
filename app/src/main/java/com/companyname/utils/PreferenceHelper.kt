package com.companyname.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {


    enum class Key {
        UNIT,
    }

    private val mSharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(WeatherApplication.getContext());
    }

    val Context.myApp: WeatherApplication get() = applicationContext as WeatherApplication

    fun setString(key: Key, value: String?): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putString(key.name, value)
        return editor.commit()
    }

    fun setInt(key: Key, value: Int): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putInt(key.name, value)
        return editor.commit()
    }

    fun setBoolean(key: Key, value: Boolean): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(key.name, value)
        return editor.commit()
    }

    fun setLong(key: Key, value: Long): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putLong(key.name, value)
        return editor.commit()
    }






    fun getBooleanVal(key: Key, defaultVal: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key.name, defaultVal)
    }

    fun getIntVal(key: Key, defaultVal: Int): Int {
        return mSharedPreferences.getInt(key.name, defaultVal)
    }

    fun getStringVal(key: Key, defaultVal: String?): String? {
        return mSharedPreferences.getString(key.name, defaultVal)
    }

    fun geLongVal(key: Key, defaultVal: Long): Long {
        return mSharedPreferences.getLong(key.name, defaultVal)
    }

}