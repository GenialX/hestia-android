package com.ihuxu.hestia_android.libs.server;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by GenialX on 11/04/2018.
 */

public class Server {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private static Server instance;

    private Server() {
        try {
            Log.d("Server", "Server construct");
            this.socket = new Socket("118.190.66.157", 1724);
            OutputStream outputStream = this.socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);

            InputStream inputStream = this.socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            this.bufferedReader = new BufferedReader(inputStreamReader);

            // Write client key to server
            String clientKeyMessage = "{\"errno\":0,\"errmsg\":\"successfully\",\"data\":{\"message_type\":1001,\"client_key\":\"mobile_client_key\",\"token\":\"aaabbbccc\"}}";
            this.writeLine(clientKeyMessage);

            Thread.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized static Server getInstance() {
        Log.d("Server", "getInstance");
        if (Server.instance == null) {
            Server.instance = new Server();
        }
       return Server.instance;
    }

    public synchronized void writeLine(String line) {
        Log.d("Server", "Server write line:" + line);
        try {
            this.bufferedWriter.write(line);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            rebuildServer();
        }
    }

    public String readLine() {
        Log.d("Server", "readLine");
        try {
            String message = this.bufferedReader.readLine();
            if (message == null) {
                throw new Exception("readLine return null");
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            rebuildServer();
        } finally {
            return null;
        }
    }

    private void rebuildServer() {
        try {
            this.socket = new Socket("118.190.66.157", 1724);
            OutputStream outputStream = this.socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);

            InputStream inputStream = this.socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            this.bufferedReader = new BufferedReader(inputStreamReader);

            // Write client key to server
            String clientKeyMessage = "{\"errno\":0,\"errmsg\":\"successfully\",\"data\":{\"message_type\":1001,\"client_key\":\"mobile_client_key\",\"token\":\"aaabbbccc\"}}";
            this.writeLine(clientKeyMessage);

            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
