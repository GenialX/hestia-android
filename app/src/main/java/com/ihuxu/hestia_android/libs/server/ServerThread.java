package com.ihuxu.hestia_android.libs.server;

import java.util.ServiceConfigurationError;
import java.util.Vector;

/**
 * Created by GenialX on 11/04/2018.
 */

public class ServerThread extends Thread {
    private static Vector<String> cmds = new Vector<>();
    private boolean doRun = false;

    @Override
    public void run() {
        doRun = true;
        while (doRun) {
            if (ServerThread.cmds.size() > 0) {
                String cmd = ServerThread.cmds.firstElement();
                Server.getInstance().writeLine(cmd);
                ServerThread.cmds.removeElementAt(0);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void pushCmd(String cmd) {
       ServerThread.cmds.add(cmd);
    }
}
