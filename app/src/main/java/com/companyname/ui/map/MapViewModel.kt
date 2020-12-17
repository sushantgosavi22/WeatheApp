package com.companyname.ui.map

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.aands.sim.simtoolkit.firebase.DatabaseHelper
import com.companyname.Model.LocationModel
import com.google.firebase.database.DatabaseError
import java.util.*

class MapViewModel(application: Application) : AndroidViewModel(application){

    fun addLocations(context: Context, locationModel: LocationModel, databaseCallBack : MapViewModel.databaseCallBack){
        DatabaseHelper.addLocations(context, locationModel, object : DatabaseHelper.onSuccessListener {
            override fun onSuccess(success: Boolean, data: ArrayList<LocationModel>?, error: DatabaseError?) {
                databaseCallBack.onCallBack(success,data,error)
            }
        })
    }

    public interface databaseCallBack{
        fun onCallBack(success: Boolean,data : List<LocationModel>?, t: DatabaseError?)
    }
}