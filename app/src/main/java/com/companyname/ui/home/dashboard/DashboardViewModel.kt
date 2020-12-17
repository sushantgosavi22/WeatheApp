package com.companyname.ui.home.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.companyname.Clients.APIClient
import com.companyname.Interfaces.ApiInterface
import com.companyname.Model.ForecastModel
import com.companyname.Model.ForecastResultModel
import com.companyname.Model.LocationModel
import com.companyname.Model.WeathersResultModel
import com.companyname.utils.PreferenceHelper
import com.companyname.R
import com.companyname.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel (application: Application) : AndroidViewModel(application) {

    fun getFiveDaysForecast(locationModel: LocationModel?, fiveDayForecastApiCallBack : FiveDayForecastApiCallBack){
        var apiServices = APIClient.client.create(ApiInterface::class.java)
        var selectedUnit = PreferenceHelper.getStringVal(PreferenceHelper.Key.UNIT, Utils.UNIT_METRIC)
        val call = apiServices.getFiveDayForecast(locationModel?.latitude.toString(),locationModel?.longitude?.toString(),APIClient.appId,selectedUnit)
        call.enqueue(object : Callback<ForecastResultModel> {
            override fun onResponse(call: Call<ForecastResultModel>, response: Response<ForecastResultModel>) {
                val jsonResponse = response.body()
                if(jsonResponse?.cod=="200"){
                    fiveDayForecastApiCallBack.onCallBack(true,jsonResponse.list,null)
                }else{
                    fiveDayForecastApiCallBack.onCallBack(false, null,Throwable("Unable to load Data..."))
                }
            }
            override fun onFailure(call: Call<ForecastResultModel>?, t: Throwable?) {
                fiveDayForecastApiCallBack.onCallBack(false,null,t)
            }
        })
    }

    fun getTodayForecast(locationModel: LocationModel?, todayForecastApiCallBack: TodayForecastApiCallBack){
        var apiServices = APIClient.client.create(ApiInterface::class.java)
        val call = apiServices.getTodayWeathers(locationModel?.latitude.toString(),locationModel?.longitude?.toString(),APIClient.appId)
        call.enqueue(object : Callback<WeathersResultModel> {
            override fun onResponse(call: Call<WeathersResultModel>, response: Response<WeathersResultModel>) {
                val jsonResponse = response.body()
                if(jsonResponse?.cod==200){
                    todayForecastApiCallBack.onCallBack(true,jsonResponse,null)
                }else{
                    todayForecastApiCallBack.onCallBack(false, null,Throwable(getApplication<Application>().getString(R.string.unable_to_load_data)))
                }
            }

            override fun onFailure(call: Call<WeathersResultModel>, t: Throwable) {
                todayForecastApiCallBack.onCallBack(false,null,t)
            }
        })
    }


    public interface FiveDayForecastApiCallBack{
        fun onCallBack(success: Boolean?, data : ArrayList<ForecastModel>?, t: Throwable?)
    }

    public interface TodayForecastApiCallBack{
        fun onCallBack(success: Boolean?, data : WeathersResultModel?, t: Throwable?)
    }

}