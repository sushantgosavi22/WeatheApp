package com.companyname.ui.home.notifications

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aands.sim.simtoolkit.firebase.DatabaseHelper
import com.companyname.Model.LocationModel

class SettingViewModel : ViewModel() {

    fun deleteAllLocations(context: Context,deleteCallBack: DeleteCallBack){
        DatabaseHelper.deleteAllLocations(context, object : DatabaseHelper.onDeleteSuccessListener {
            override fun onSuccess(success: Boolean) {
                deleteCallBack.onDeleteCallBack(success)
            }
        })
    }


    public interface DeleteCallBack{
        fun onDeleteCallBack(success: Boolean)
    }
}