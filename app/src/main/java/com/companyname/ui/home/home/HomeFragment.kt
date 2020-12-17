package com.companyname.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.Model.LocationModel
import com.companyname.R
import com.companyname.utils.Utils
import com.companyname.adapters.LocationAdapter
import com.companyname.ui.home.HomeActivityViewModel
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), LocationAdapter.OnLocationClickListener, HomeViewModel.databaseCallBack, HomeViewModel.DeleteCallBack {
    var dataList: ArrayList<LocationModel> = ArrayList()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeActivityViewModel: HomeActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeActivityViewModel = activity?.run {
            ViewModelProviders.of(this).get(HomeActivityViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(dataList)
        subscribeDataCallBack()
    }

    private fun subscribeDataCallBack() {
        context?.let {
            showProgress()
            homeViewModel.getLocationsFromDatabase(it, this)
        }
    }

    private fun setAdapter(list: List<LocationModel>) {
        dataList = ArrayList(list)
        if (list.isNotEmpty()) {
            emptyView.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
            emptyView.text = getString(R.string.no_record_found)
        }
        locationAdapter.setAppList(list)
    }


    private lateinit var locationAdapter: LocationAdapter

    private fun setRecyclerView(dataList: ArrayList<LocationModel>) {
        locationAdapter = LocationAdapter(this)
        val categoryLinearLayoutManager = LinearLayoutManager(context)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerLocationList.layoutManager = categoryLinearLayoutManager
        locationAdapter.setAppList(dataList)
        recyclerLocationList.adapter = locationAdapter
    }


    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }


    override fun onLocationClick(data: LocationModel?) {
        data?.let {
            homeActivityViewModel.select(data)
            val navController = activity?.findNavController(R.id.nav_host_fragment)
            navController?.navigate(R.id.navigation_city)
        }
    }

    override fun onLocationDelete(data: LocationModel?) {
        showProgress()
        data?.let {
            context?.let {
                homeViewModel.deleteLocations(it,data,this)
            }
        }
    }


    override fun onCallBack(success: Boolean?, data: List<LocationModel>?, t: DatabaseError?) {
        t?.let { throwable ->
            context?.let { con ->
                Utils.showToast(con, throwable.message)
            }
        }
        hideProgress()
        if (data != null &&  data.isNotEmpty()) {
            setAdapter(data)
        }
    }

    override fun onDeleteCallBack(success: Boolean) {
        hideProgress()
        context?.let { context->
            if (success) {
                Utils.showToast(context, this.getString(R.string.delete_location))
                subscribeDataCallBack()
            } else {
                Utils.showToast(context, this.getString(R.string.unable_to_delete_loc))
            }
        }
    }
}