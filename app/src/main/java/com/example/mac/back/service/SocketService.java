package com.example.mac.back.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.mac.back.R;
import com.example.mac.back.utils.NotifyUtils;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by mac on 2018/4/10.
 */

public class SocketService extends Service{

    /**心跳频率*/
    private static final long HEART_BEAT_RATE = 10 * 1000;//10s
    /**服务器ip地址*/
    public static final String HOST = "192.168.1.101";// "192.168.1.21";//
    /**服务器端口号*/
    public static final int PORT = 30001;

    private long sendTime = 0L;
    /**读线程*/
    private ClientThread mReadThread;
    private Socket so;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**心跳任务，不断重复调用自己*/
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                sendMsg("HeartBeat");//就发送一个\r\n过去 如果发送失败，就重新初始化一个socket
            }
            handler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    Handler handler= new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                //s.append("\n" + msg.obj.toString());
                Logger.e(msg.obj.toString());
                NotifyUtils notifyUtils=new NotifyUtils(getApplicationContext(),msg.obj.toString());
                notifyUtils.show();
                //   s=new StringBuffer();
            }else if(msg.what == 0x666){
                Logger.e("收到心跳表示socket还在不需要初始化");
            }
        };
    };


    @Override
    public void onCreate() {
        //提高进程优先级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(250, builder.build());
            startService(new Intent(this, CancelService.class));
        } else {
            startForeground(250, new Notification());
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new InitSocketThread().start();//初始化socket线程
        return super.onStartCommand(intent, flags, startId);
    }


    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    private void initSocket() {//初始化Socket
        try {
            so = new Socket(HOST, PORT);
            mReadThread = new ClientThread();
            mReadThread.start();
            handler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ClientThread extends Thread{//

        //该线程所处理的Socket对应的输入流
        BufferedReader br = null;
        private boolean isStart = true;

        @Override
        public void run() {
            if (null != so) {
                try {
                    String content = null;
                    br = new BufferedReader(new InputStreamReader(so.getInputStream()));
                    while (!so.isClosed() && !so.isInputShutdown()
                            && isStart && (content = br.readLine()) != null) {
                        Message msg = new Message();
                        msg.obj =content;
                        //收到服务器过来的消息，就通过Broadcast发送出去
                        if(content.equals("HeartBeat——返回自服务器")){//处理心跳回复
                            msg.what = 0x666;
                            handler.sendMessage(msg);
                        }else{
                            msg.what = 0x123;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void release() {
            isStart = false;
            releaseLastSocket(so);
        }

    }


    /**
     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
     * @param mSocket
     */
    private void releaseLastSocket(Socket mSocket) {
        try {
            if (null != mSocket) {
                if (!mSocket.isClosed()) {
                    mSocket.close();
                }
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(final String msg) {//向服务端写入HeartBeat,若服务器没有回复就是断开不成功
        if (null == so ) {
            return;
        }
        if (!so.isClosed() && !so.isOutputShutdown()) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        OutputStream os = so.getOutputStream();
                        String message = msg + "\r\n";
                        os.write(message.getBytes());
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
                    if (!aBoolean) {
                        Logger.e("初始化socket");
                        handler.removeCallbacks(heartBeatRunnable);
                        mReadThread.release();
                        releaseLastSocket(so);
                        new InitSocketThread().start();
                    }
                }
            }.execute();
        } else {
            return;
        }
    }

}
