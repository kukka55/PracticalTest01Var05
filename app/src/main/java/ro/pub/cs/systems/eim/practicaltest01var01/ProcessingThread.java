package ro.pub.cs.systems.eim.practicaltest01var01;

import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    int LocalSum = 0;
    boolean running = true;
    boolean msg = false;

    public ProcessingThread(Context context, int Sum) {
        this.context = context;
        LocalSum = Sum;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while(running) {
            sleep();
            if(!msg) {
                sendMessage();
                msg = true;
            }
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("Sum10");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + " Sum > 10 : " + LocalSum);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        running = false;
    }
}

