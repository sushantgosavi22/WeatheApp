package com.companyname.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.companyname.Model.ForecastModel
import com.companyname.R
import kotlinx.android.synthetic.main.forecast_item.view.*
import java.util.*

class ForecastAdapter() : RecyclerView.Adapter<ForecastAdapter.RecyclerHolderForecastDetails>() {
    private val forecastList = ArrayList<ForecastModel>()

    fun setForecastList(forecastModel: List<ForecastModel>) {
        forecastList.clear()
        forecastList.addAll(forecastModel)
        notifyDataSetChanged()
    }

    fun clearList() {
        forecastList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: RecyclerHolderForecastDetails, position: Int) {
        val appInfo = forecastList[position]
        holder.bind(appInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolderForecastDetails {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.forecast_item, parent, false)
        return RecyclerHolderForecastDetails(view)
    }

    inner class RecyclerHolderForecastDetails(var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(appInfo: ForecastModel?) {
            appInfo?.let {
                view.tvMain.text = appInfo.weather?.first()?.main
                view.tvMainDescription.text = appInfo.weather?.first()?.description
                view.tvHumidityValue.text = appInfo.main?.humidity.toString()
                view.tvTempValue.text = appInfo.main?.temp.toString()
                view.tvWindValue.text = appInfo.wind?.speed.toString()
                view.tvRainChanceValue.text = getRain(appInfo.rain)
            }
        }
    }

    fun getRain(rain: HashMap<String, String>?): String {
        var rainValue = ""
        rain?.let {
            if (rain.isNotEmpty()) {
                rainValue = rainValue.plus(rain.entries.first().key).plus("/").plus(rain.entries.first().value)
            }
        }
        return rainValue
    }


}