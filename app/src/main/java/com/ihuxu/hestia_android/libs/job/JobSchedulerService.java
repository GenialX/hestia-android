package com.ihuxu.hestia_android.libs.job;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.ihuxu.hestia_android.libs.Location;

/**
 * Created by GenialX on 12/04/2018.
 */

public class JobSchedulerService extends JobService {
    private LocationManager locationManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("jobSchedulerService", "handleMessage event");
        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.removeMessages( 1 );
        return false;
    }

    private Handler mJobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.w("jobSchedulerService", "permission failed");

            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0.01f, new Location());
            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }

    });
}
