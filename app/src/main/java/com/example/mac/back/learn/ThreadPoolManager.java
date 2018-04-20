package com.example.mac.back.learn;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

/**
 *
 *  处理高并发场景 就是很多线程同时访问
 * Created by mac on 2018/4/11.
 */

public class ThreadPoolManager {


    //静态内部类模式的单例

    private static class ThreadPoolManagerHolder{
        private static final ThreadPoolManager INSTANCE=new ThreadPoolManager();
    }
    private ThreadPoolManager(){
        //核心线程数4个，最大线程10个，空闲线程存活10s，线程任务缓冲队列4个线程量
        threadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),handler);
        threadPoolExecutor.execute(runnable);
    };

    public static ThreadPoolManager getInstance(){
        return ThreadPoolManagerHolder.INSTANCE;//静态内部类在调用此函数时才加载
    }


    private ThreadPoolExecutor threadPoolExecutor;//预定义线程执行器
    private LinkedBlockingQueue<Future<?>> service=new LinkedBlockingQueue<>();//阻塞队列

    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                //被拒绝后重新排队
                service.put(new FutureTask<Object>(runnable,null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (true) { //循环从阻塞队列拿线程执行
                FutureTask futureTask = null;
                try {
                    Log.e("myThreadPook","service size "+service.size());
                    futureTask = (FutureTask) service.take();
                    Log.e("myThreadPook","池  "+threadPoolExecutor.getPoolSize());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask != null) {
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    };


    public <T> void  execute(final FutureTask<T> futureTask, Object delayed){
        if(futureTask!=null){
            try {
                if(delayed!=null){
                    Timer timer=new Timer();
                    timer.schedule(new TimerTask(){//延迟放入队列
                        public void run(){
                            try {
                                service.put(futureTask);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }},(long)delayed);
                }else {
                    service.put(futureTask);//直接放入队列
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
