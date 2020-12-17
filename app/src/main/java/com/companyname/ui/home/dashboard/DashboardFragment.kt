package com.companyname.ui.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.Model.ForecastModel
import com.companyname.Model.LocationModel
import com.companyname.Model.WeathersResultModel
import com.companyname.R
import com.companyname.utils.Utils
import com.companyname.adapters.ForecastAdapter
import com.companyname.ui.home.HomeActivityViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.emptyView
import kotlinx.android.synthetic.main.fragment_home.progressBar


class DashboardFragment : Fragment() ,DashboardViewModel.FiveDayForecastApiCallBack,DashboardViewModel.TodayForecastApiCallBack {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var homeActivityViewModel: HomeActivityViewModel
    private var locationModel : LocationModel? = null
    var dataList: ArrayList<ForecastModel> = ArrayList()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        homeActivityViewModel = activity?.run {
            ViewModelProviders.of(this).get(HomeActivityViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(dataList)
        setUpView()
    }

    private fun setUpView(){
        radioGroup.check(R.id.today)
        homeActivityViewModel.selected.observe(viewLifecycleOwner, Observer<LocationModel> { item ->
            locationModel = item
            tvLocationName.visibility = View.VISIBLE
            tvLocationName.text = item.locationName?.capitalize()
            callApi(radioGroup.checkedRadioButtonId)
        })
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            callApi(i)
        }
    }

    private fun callApi(radioButtonId: Int){
        locationModel?.let {
            when (radioButtonId) {
                R.id.today -> {
                    subscribeTodayForecastCallBack()
                }
                R.id.fiveDay -> {
                    subscribeFiveDaysForecastCallBack()
                }
                else->{
                    subscribeTodayForecastCallBack()
                }
            }
        }?: context?.let { Utils.showToast(it,it.getString(R.string.empty_result)) }

    }
    private fun subscribeFiveDaysForecastCallBack() {
        context?.let {
            if (Utils.isInternetConnected(it)) {
                showProgress()
                dashboardViewModel.getFiveDaysForecast( locationModel, this)
            } else {
                Utils.showToast(it, getString(R.string.no_internet_connection))
            }
        }
    }

    private fun subscribeTodayForecastCallBack() {
        context?.let {
            if (Utils.isInternetConnected(it)) {
                showProgress()
                dashboardViewModel.getTodayForecast( locationModel, this)
            } else {
                Utils.showToast(it, getString(R.string.no_internet_connection))
            }
        }
    }

    private fun setAdapter(list: List<ForecastModel>) {
        forecastAdapter?.clearList()
        dataList = ArrayList(list)
        if (list.isNotEmpty()) {
            emptyView.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
            emptyView.text = getString(R.string.no_record_found)
        }
        forecastAdapter.setForecastList(list)
    }


    private lateinit var forecastAdapter: ForecastAdapter

    private fun setRecyclerView(dataList: ArrayList<ForecastModel>) {
        forecastAdapter = ForecastAdapter()
        val categoryLinearLayoutManager = LinearLayoutManager(context)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerWeatherList.layoutManager = categoryLinearLayoutManager
        forecastAdapter.setForecastList(dataList)
        recyclerWeatherList.adapter = forecastAdapter
    }


    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onCallBack(success: Boolean?, data : ArrayList<ForecastModel>?, t: Throwable?) {
        hideProgress()
        t?.let {
            activity?.let { activity -> Utils.showToast(activity,t.message) }
        }
        if(t==null && success==true){
            data?.let {
                setAdapter(it)
            }
        }
    }

    override fun onCallBack(success: Boolean?, data: WeathersResultModel?, t: Throwable?) {
        hideProgress()
        t?.let {
            activity?.let { activity -> Utils.showToast(activity,t.message) }
        }
        if(t==null && success==true){
            data?.let {
                var list = ArrayList<ForecastModel>()
                var model = ForecastModel()
                model.weather = it.weather
                model.wind = it.wind
                model.main = it.main
                model.rain = it.rain
                list.add(model)
                setAdapter(list)
            }
        }
    }
}