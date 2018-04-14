package com.ihuxu.hestia_android.libs.server;

import android.util.Log;

public class ServerThread extends Thread {
    @Override
    public void run() {
        boolean go = true;
        while (true) {
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
}
