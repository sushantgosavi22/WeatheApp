package com.companyname.ui.map

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.companyname.Model.LocationModel
import com.companyname.R
import com.companyname.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseError
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapViewModel.databaseCallBack {
    private lateinit var mapViewModel: MapViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener { point ->
            var latLong = LatLng(point.latitude, point.longitude)
            var locName = getName(latLong)
            val marker = MarkerOptions().position(latLong).title("" +locName )
            googleMap.addMarker(marker)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong))
            mapViewModel.addLocations(this, LocationModel(latLong.latitude,latLong.longitude,locName,System.currentTimeMillis()), this)
        }
    }

    private fun getName(latLong: LatLng): String {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? = geoCoder.getFromLocation(latLong.latitude, latLong.longitude, 1)
        return if (!addresses.isNullOrEmpty()) {
            addresses.first().getAddressLine(0) ?: "Point ".plus(System.currentTimeMillis())
        } else {
            "Point ".plus(System.currentTimeMillis())
        }
    }

    override fun onCallBack(success: Boolean, data: List<LocationModel>?, t: DatabaseError?) {
        if (success) {
            val locationName = data?.first()?.locationName?:""
            Utils.showToast(this, locationName.plus(" ").plus(getString(R.string.add_location_successfully)))
        } else {
            t?.let { throwable ->
                Utils.showToast(this, throwable.message)
            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}