package com.aands.sim.simtoolkit.firebase

import android.content.Context
import com.companyname.Model.LocationModel
import com.companyname.utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


object DatabaseHelper {

    private fun getLocationsTable(): String {
        return Utils.LOCATIONS_TABLE
    }

    public fun getLocations(context: Context, listener: onSuccessListener) {
        FirebaseHelper.getInstance(context).get(getLocationsTable(), object : FirebaseHelper.GetListener {
            override fun onGet(path: String?, data: DataSnapshot?) {
                data?.let {
                    var locationModelList = getListData(data, LocationModel::class.java)
                    listener.onSuccess(true, locationModelList, null)
                } ?: listener.onSuccess(false, null, null)
            }
        }).setErrorListener(object : FirebaseHelper.ErrorListener {
            override fun onError(error: DatabaseError) {
                listener.onSuccess(false, null, error)
            }
        })
    }

    public fun addLocations(context: Context, locationModel: LocationModel, listener: onSuccessListener) {
        locationModel.firebaseId = locationModel.timestamp
        FirebaseHelper.getInstance(context).set(getLocationsTable(), "" + locationModel.timestamp, locationModel, object : FirebaseHelper.SetListener {
            override fun onSet(path: String?) {
                var list = ArrayList<LocationModel>()
                list.add(locationModel)
                listener.onSuccess(true, list, null)
            }
        }).setErrorListener(object : FirebaseHelper.ErrorListener {
            override fun onError(error: DatabaseError) {
                listener.onSuccess(false, null, error)
            }
        })
    }

    public fun deleteLocations(context: Context, locationModel: LocationModel, listener: onDeleteSuccessListener) {
        FirebaseHelper.getInstance(context).get(getLocationsTable()).child(getLocationsTable().plus("/").plus(locationModel.timestamp)).removeValue { error, ref -> listener.onSuccess((error == null)) }
        listener.onSuccess(true)
    }

    public fun deleteAllLocations(context: Context, listener: onDeleteSuccessListener) {
        FirebaseHelper.getInstance(context).get(getLocationsTable()).child(getLocationsTable()).removeValue { error, ref -> listener.onSuccess((error == null)) }
        listener.onSuccess(true)
    }

    interface onSuccessListener {
        fun onSuccess(success: Boolean, data: ArrayList<LocationModel>?, error: DatabaseError?)
    }

    interface onDeleteSuccessListener {
        fun onSuccess(success: Boolean)
    }


    fun <T> getListData(dataSnapshot: DataSnapshot, valueType: Class<T>): ArrayList<T> {
        var list = ArrayList<T>()
        if (null != dataSnapshot.value) {
            for (children in dataSnapshot.children) {
                try {
                    val model = children.getValue(valueType)
                    if (null != model) {
                        list.add(model)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return list
    }
}