package com.companyname.Model

import com.aands.sim.simtoolkit.firebase.BaseFirebaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeathersResultModel : Serializable {
    @SerializedName("weather")
    var weather: ArrayList<Weather>? = null

    @SerializedName("main")
    var main: Main? = null

    @SerializedName("wind")
    var wind : Wind? =null

    @SerializedName("name")
    var name = ""

    @SerializedName("rain")
    var rain : HashMap<String,String>? = null

    @SerializedName("cod")
    var cod = 0

    @SerializedName("id")
    var id = 0

}

data class Weather(val id : Int? =0,val main : String? = "",val description : String? = "" ): Serializable
data class Main(val temp : Double? =0.0,val humidity : Int? =0,val pressure : Int? = 0 ): Serializable
data class Wind(val speed : Double? =0.0,val deg : Int? = 0 ): Serializable

class ForecastResultModel : Serializable {
    @SerializedName("list")
    var list: ArrayList<ForecastModel>? = null

    @SerializedName("cod")
    var cod = ""

    @SerializedName("cnt")
    var cnt = 0
}

class ForecastModel  : Serializable{

    @SerializedName("weather")
    var weather: ArrayList<Weather>? = null

    @SerializedName("main")
    var main: Main? = null

    @SerializedName("wind")
    var wind : Wind? =null

    @SerializedName("rain")
    var rain : HashMap<String,String>? = null
}