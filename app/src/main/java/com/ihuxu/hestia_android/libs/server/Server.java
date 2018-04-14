package com.ihuxu.hestia_android.libs.server;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by GenialX on 11/04/2018.
 */

public class Server {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private static Server instance;

    private Server() {
        try {
            Log.d("Server", "Server construct");
            this.socket = new Socket("118.190.66.157", 1724);
            OutputStream outputStream = this.socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Server getInstance() {
        if (Server.instance == null) {
            Server.instance = new Server();
        }
       return Server.instance;
    }

    public void writeLine(String line) {
        try {
            this.bufferedWriter.write(line);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            rebuildServer();
            try {
                this.bufferedWriter.write(line);
                this.bufferedWriter.newLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void rebuildServer() {
        try {
            this.socket = new Socket("118.190.66.157", 1724);
            OutputStream outputStream = this.socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
