package com.example.mac.back.learn;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.view.ViewManager;
import android.view.WindowManager;

import com.example.mac.back.ui.activity.MainActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by mac on 2018/4/9.
 */

public class Study {



    HandlerThread handlerThread;

Executor executor;

Executors executors;

    public void main(){


        new Thread().start();

        ThreadPoolExecutor tp1= (ThreadPoolExecutor) Executors.newFixedThreadPool(5);//创建固定大小的线程池
        ThreadPoolExecutor tp2= (ThreadPoolExecutor)Executors.newCachedThreadPool();//新任务会创建新线程处理 空闲久的线程会销毁 可变大小先吃
        ThreadPoolExecutor tp3= (ThreadPoolExecutor)Executors.newSingleThreadExecutor(); //单个线程处理单个任务


        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                //只有这个方法在子线程执行
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();


        WindowManager windowManager;


//        ViewManager viewManager = MainActivity.this.getWindowManager();
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
//                200, 200, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
//                WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.OPAQUE);
//        viewManager.addView(view,layoutParams);


        IntentService intentService=new IntentService("xxx") {
            @Override
            protected void onHandleIntent(@Nullable Intent intent) {

            }
        };

//        Context context;
//        context.startService(intentService);





    }





}
