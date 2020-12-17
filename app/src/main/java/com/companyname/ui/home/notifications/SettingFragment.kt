package com.companyname.ui.home.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.companyname.utils.PreferenceHelper
import com.companyname.R
import com.companyname.utils.Utils
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment(),SettingViewModel.DeleteCallBack {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
                ViewModelProvider(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        var selectedUnit = PreferenceHelper.getStringVal(PreferenceHelper.Key.UNIT, Utils.UNIT_METRIC)
        var radioButtonId = if(selectedUnit.equals(Utils.UNIT_METRIC,true)){
            R.id.metric
        }else{
            R.id.imperial
        }
        radioGroupUnit.check(radioButtonId)
        radioGroupUnit.setOnCheckedChangeListener { radioGroup, id ->
            when(id){
                R.id.metric ->{
                    PreferenceHelper.setString(PreferenceHelper.Key.UNIT, Utils.UNIT_METRIC)
                }
                R.id.imperial ->{
                    PreferenceHelper.setString(PreferenceHelper.Key.UNIT, Utils.UNIT_IMPERIAL)
                }
               else ->{
                   PreferenceHelper.setString(PreferenceHelper.Key.UNIT, Utils.UNIT_METRIC)
                }
            }
        }

        btnClearCity.setOnClickListener {
            context?.let { context -> settingViewModel.deleteAllLocations(context,this) }
        }
    }

    override fun onDeleteCallBack(success: Boolean) {
        context?.let { context->
            if (success) {
                Utils.showToast(context, this.getString(R.string.delete_location))
            } else {
                Utils.showToast(context, this.getString(R.string.unable_to_delete_loc))
            }
        }
    }
}