package com.ihuxu.hestia_android.libs.server;

import android.util.Log;

public class ServerThread extends Thread {

    private static boolean doWorking = false;

    @Override
    public void run() {
        if (doWorking == false) {
            doWorking = true;
        }
        while (doWorking) {
            try {
                Log.i("ServerThread", "Do work...");
                String message = MessageQueue.popMessage();
                if (message != "") {
                    Server.getInstance().writeLine(message);
                }
                this.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean doWorking() {
        return doWorking;
    }
}
