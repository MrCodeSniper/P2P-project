package com.example.mac.back.data;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.Socket;

/**
 * Created by mac on 2018/3/21.
 */

public class ClientThread extends Thread{

    private Handler handler;
    //该线程所处理的Socket对应的输入流
    BufferedReader br = null;

    private WeakReference<Socket> mWeakSocket;


    public ClientThread(Socket s, Handler handler) throws IOException {
        mWeakSocket = new WeakReference<Socket>(s);
        this.handler = handler;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            String content = null;
            //不断读取Socket输入流中的内容
            while ((content = br.readLine()) != null) {
                //每当读到来自服务器的数据后，发送消息通知程序界面显示数据
                Message msg = new Message();
                msg.what = 0x123;
                msg.obj = content;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
