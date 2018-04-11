package com.ihuxu.hestia_android.libs;

import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.ihuxu.hestia_android.libs.server.ServerThread;

/**
 * Created by GenialX on 10/04/2018.
 */

public class Location implements LocationListener {
    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.d("Location", "receive onLocationChanged event, the location info:" + location.toString());
        String cmd = "{\"errno\":0,\"errmsg\":\"successfully\",\"data\":{\"message_type\":1000,\"lat\":"
                + location.getLatitude() + ",\"lnt\":" + location.getLongitude() + ",\"token\":\"aaabbbccc\"}}";
        Log.d("Location", "receive onLocationChanged event, the location info:" + cmd);
        ServerThread.pushCmd(cmd);
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
