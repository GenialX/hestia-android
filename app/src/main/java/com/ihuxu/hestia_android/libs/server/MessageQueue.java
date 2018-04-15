package com.ihuxu.hestia_android.libs.server;

import java.util.Vector;

public class MessageQueue {
    private static Vector<String> queue = new Vector<String>();
    private static Vector<String> inQueue = new Vector<String>();

    public static void pushMessage(String message) {
        MessageQueue.queue.add(message);
    }

    public static String popMessage() {
        if (MessageQueue.queue.size() > 0) {
            String message = MessageQueue.queue.elementAt(0);
            MessageQueue.queue.removeElementAt(0);
            return message;
        }
        return "";
    }

    public static String getFirstMessage() {
        if (MessageQueue.queue.size() > 0) {
            String message = MessageQueue.queue.elementAt(0);
            return message;
        }
        return "";
    }

    public static void pushInMessage(String message) {
        MessageQueue.inQueue.add(message);
    }

    public static String popInMessage() {
        if (MessageQueue.inQueue.size() > 0) {
            String message = MessageQueue.inQueue.elementAt(0);
            MessageQueue.inQueue.removeElementAt(0);
            return message;
        }
        return "";
    }

    public static String getFirstInMessage() {
        if (MessageQueue.inQueue.size() > 0) {
            String message = MessageQueue.inQueue.elementAt(0);
            return message;
        }
        return "";
    }
}
