package com.example.mac.back.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.mac.back.data.ClientThread;
import com.example.mac.back.utils.NotifyUtils;
import com.example.mac.back.utils.RemoveHZ;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by mac on 2018/3/21.
 */

public class PushService extends IntentService {

    Handler handler= new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                //s.append("\n" + msg.obj.toString());
                Logger.e(msg.obj.toString());
                NotifyUtils notifyUtils=new NotifyUtils(getApplicationContext(),msg.obj.toString());
                notifyUtils.show();
             //   s=new StringBuffer();
            }
        };
    };

    private Socket clientSocket = null;
    StringBuffer s=new StringBuffer();



    public PushService() {
        super("push thread");
    }



    @Override
    protected void onHandleIntent( Intent intent) {


        //做耗时操作
        try {
            clientSocket= new Socket("192.168.1.101",30001);
            new Thread(new ClientThread(clientSocket, handler)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface UpdateUI{
        void updateUI();
}



}
