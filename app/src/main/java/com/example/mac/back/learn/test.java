package com.example.mac.back.learn;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;

import java.util.concurrent.locks.Lock;

/**
 * Created by mac on 2018/3/11.
 */

public class test {

    Activity activity;

    Window window;

   class view extends ViewGroup{


       public view(Context context) {
           super(context);
       }

       @Override
       protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

       }

       @Override
       public boolean onInterceptTouchEvent(MotionEvent ev) {
           return super.onInterceptTouchEvent(ev);
       }

       @Override
       public boolean dispatchTouchEvent(MotionEvent ev) {
           return super.dispatchTouchEvent(ev);
       }




   }








}
