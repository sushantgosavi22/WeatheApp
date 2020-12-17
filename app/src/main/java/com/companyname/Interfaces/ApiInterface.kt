package com.companyname.Interfaces

import com.companyname.Model.ForecastResultModel
import com.companyname.Model.WeathersResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("weather")
    fun getTodayWeathers(@Query("lat") lat: String?, @Query("lon") lon: String?, @Query("appid") appid: String?): Call<WeathersResultModel>

    @GET("forecast")
    fun getFiveDayForecast(@Query("lat") lat: String?, @Query("lon") lon: String?, @Query("appid") appid: String?, @Query("units") units: String?): Call<ForecastResultModel>
}