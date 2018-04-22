package com.ihuxu.hestia_android.libs.server;

import android.util.Log;

public class ServerReadThread extends Thread {

    private static boolean doWorking = false;

    @Override
    public void run() {
        if (doWorking == false) {
            doWorking = true;
        }

        while (doWorking) {
            Log.i("ServerReadThread", "Do work...");
            // Read message from server

            String message = Server.getInstance().readLine();
            Log.i("ServerReadThread", "Read message:" + message);
            if (message != null) {
                MessageQueue.pushInMessage(message);
            }
        }
    }

    public static boolean doWorking() {
        return doWorking;
    }
}
