package com.ihuxu.hestia_android.libs.server;

import android.util.Log;

public class ServerWriteThread extends Thread {

    private static boolean doWorking = false;

    @Override
    public void run() {
        if (doWorking == false) {
            doWorking = true;
        }

        // Write client key to server
        String clientKeyMessage = "{\"errno\":0,\"errmsg\":\"successfully\",\"data\":{\"message_type\":1001,\"client_key\":\"mobile_client_key\",\"token\":\"aaabbbccc\"}}";
        Server.getInstance().writeLine(clientKeyMessage);

        int i = 0;
        while (doWorking) {
            try {
                Log.i("ServerWriteThread", "Do work...");

                // per 2s
                if (i % 2 == 0) {
                    // Send location info to server
                    String message = MessageQueue.popMessage();
                    if (message != "") {
                        Server.getInstance().writeLine(message);
                    }
                }

                // per 1m
                if (i % 30  == 0) {
                    // Get home device info from server
                    String requestMessage = "{\"errno\":0,\"errmsg\":\"successfully\",\"data\":{\"message_type\":1100,\"token\":\"aaabbbccc\"}}";
                    Server.getInstance().writeLine(requestMessage);
                }

                this.sleep(1000);
                ++i;
                if (i > 60 * 100) {
                    i = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean doWorking() {
        return doWorking;
    }
}
