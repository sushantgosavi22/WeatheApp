package com.companyname.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aands.sim.simtoolkit.firebase.DatabaseHelper
import com.companyname.Model.LocationModel
import com.google.firebase.database.DatabaseError
import java.util.*

class HomeActivityViewModel(application: Application) : AndroidViewModel(application){

    var selected = MutableLiveData<LocationModel>()

    fun select(item: LocationModel) {
        selected.value = item
    }
}