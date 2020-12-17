package com.companyname.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.companyname.Model.LocationModel
import com.companyname.utils.Utils
import com.companyname.databinding.AppItemBinding
import java.util.*

class LocationAdapter(private var listener: OnLocationClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val locationList = ArrayList<LocationModel>()

    fun setAppList(categoryModel: List<LocationModel>) {
        locationList.clear()
        locationList.addAll(categoryModel)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val locationModel = locationList[position]
        (holder as LocationAdapter.RecyclerHolderCatIcon).bind(locationModel, listener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = AppItemBinding.inflate(layoutInflater, parent, false)
        return RecyclerHolderCatIcon(applicationBinding)
    }

    interface OnLocationClickListener {
        fun onLocationClick(data: LocationModel?)
        fun onLocationDelete(data: LocationModel?)
    }


    inner class RecyclerHolderCatIcon(private var applicationBinding: AppItemBinding) : RecyclerView.ViewHolder(applicationBinding.root) {
        fun bind(appInfo: LocationModel, listener: OnLocationClickListener?) {
            applicationBinding.locationName.text = appInfo.locationName?.capitalize()
            applicationBinding.time.text = Utils.getLocalFormatterDate(appInfo.timestamp)
            applicationBinding.ivDelete.setOnClickListener {
                listener?.onLocationDelete(appInfo)
            }
            applicationBinding.itemCard.setOnClickListener {
                listener?.onLocationClick(appInfo)
            }
        }
    }
}