package com.ihuxu.hestia_android.libs;

import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by GenialX on 10/04/2018.
 */

public class Location implements LocationListener {
    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.d("Location", "receive onLocationChanged event, the location info:" + location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
