package com.companyname.ui.home.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aands.sim.simtoolkit.firebase.DatabaseHelper
import com.companyname.Model.LocationModel
import com.google.firebase.database.DatabaseError
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text



    public interface DeleteCallBack{
        fun onDeleteCallBack(success: Boolean)
    }

    fun deleteLocations(context: Context, locationModel: LocationModel, deleteCallBack: DeleteCallBack){
        DatabaseHelper.deleteLocations(context, locationModel, object : DatabaseHelper.onDeleteSuccessListener {
            override fun onSuccess(success: Boolean) {
                deleteCallBack.onDeleteCallBack(success)
            }
        })
    }


    fun getLocationsFromDatabase(context: Context, databaseCallBack : HomeViewModel.databaseCallBack){
        DatabaseHelper.getLocations(context, object : DatabaseHelper.onSuccessListener {
            override fun onSuccess(success: Boolean, data: ArrayList<LocationModel>?, error: DatabaseError?) {
                databaseCallBack.onCallBack(success, data, error)
            }
        })
    }

    public interface databaseCallBack{
        fun onCallBack(success: Boolean?,data : List<LocationModel>?, t: DatabaseError?)
    }
}