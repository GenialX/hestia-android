package com.ihuxu.hestia_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ihuxu.hestia_android.libs.server.MessageQueue;
import com.ihuxu.hestia_android.libs.server.ServerReadThread;
import com.ihuxu.hestia_android.libs.server.ServerWriteThread;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationListener mLocationListener = null;

    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Server Thread
        new ServerWriteThread().start();
        new ServerReadThread().start();

        // Location
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                String message = "{\"errno\":0,\"errmsg\":\"successfully\",\"data\":{\"message_type\":1000,\"lat\":"
                        + aMapLocation.getLatitude() + ",\"lnt\":" + aMapLocation.getLongitude() + ",\"token\":\"aaabbbccc\"}}";
                Log.i("AMapLocationListener", "receive onLocationChanged event, the location info:" + message + " " + aMapLocation.toString());
                MessageQueue.pushMessage(message);
            }
        };
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(60000);

        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.enableBackgroundLocation(2001, buildNotification());
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        // UI sub Thread
        new Thread(new Runnable() {
            public void run() {
                String lastInMessage = "";
                while (true) {
                    Message message = mHandler.obtainMessage();
                    message.what = 1;
                    String sendMessge = MessageQueue.getFirstMessage();
                    // Server Thread status
                    message.obj = "The status of Server Thread: " + ServerReadThread.doWorking() + "\n\n";
                    // UI sub-Thread status
                    message.obj += "The status of UI sub-Thread: true" + "\n\n";
                    // Message to send
                    if (sendMessge == "") {
                        message.obj += "There is not any message in queue to send" + "\n\n";
                        ;
                    } else {
                        message.obj += "The next message to send: " + sendMessge + "\n\n";
                    }
                    // Home Device Info
                    if (MessageQueue.getFirstInMessage() != "") {
                        lastInMessage = MessageQueue.popInMessage();
                    }
                    message.obj += "The home device info:" + lastInMessage + "\n\n";
                    message.obj += "Pretending to be mysterious: " + Math.random() + "\n\n";
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mTextMessage.setText((String)msg.obj);
        }
    };

    private Notification buildNotification() {
        Notification.Builder builder = null;
        Notification notification = null;
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = getPackageName();
            if(!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setShowBadge(true);
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_desc))
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }
}
