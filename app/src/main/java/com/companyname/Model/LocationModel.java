package com.companyname.Model;

import com.aands.sim.simtoolkit.firebase.BaseFirebaseModel;

import java.io.Serializable;

public class LocationModel extends BaseFirebaseModel implements Serializable {
    double latitude;
    double longitude;
    String locationName;
    long timestamp;

    public LocationModel() {

    }

    public LocationModel(double latitude, double longitude, String locationName, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
