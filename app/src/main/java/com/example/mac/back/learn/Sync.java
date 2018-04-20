package com.example.mac.back.learn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kotlin.jvm.Synchronized;

/**
 * Created by mac on 2018/4/10.
 */

public class Sync {



    public static void main(String[] args){

        final Sync sync1=new Sync();
        final Sync sync2=new Sync();
        final Lock lock = new ReentrantLock();// 锁对象

        new Thread(new Runnable() {
            @Override
            public void run() {
                sync1.method1();//锁了this 即sync对象
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                sync1.method2();//锁了this 即sync对象
            }
        }).start();

    }


    public synchronized void method1(){
        System.out.println("method1 start");
        try{
            System.out.println("method1 excute");
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("method1 end");
    }

    public synchronized void method2(){
        System.out.println("method2 start");
        try{
            System.out.println("method2 excute");
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("method2 end");
    }

    public void output(String name,Lock lock) {
        lock.lock();      // 得到锁
        try {
            for(int i = 0; i < name.length(); i++) {
                System.out.print(name.charAt(i));
            }
        } finally {
            lock.unlock();// 释放锁
        }
    }


}



//    没有同步
//    method1 start
//    method1 excute
//    method2 start
//    method2 excute
//    method2 end
//    method1 end


//   修饰普通函数 同步成功  不同对象无法同步 对象的monitor
//    method1 start
//    method1 excute
//    method1 end
//    method2 start
//    method2 excute
//    method2 end


//修饰静态函数 同步成功 同对象也可以同步

//对静态方法的同步本质上是对类的同步 （静态方法本质上是属于类的方法，而不是对象上的方法）
//属于同类的不同对象也可以顺序执行
//调用的时候需要获取同一个类上monitor（每个类只对应一个class对象）


//对于代码块的同步实质上需要获取Synchronized关键字后面括号中对象的monitor
//由于这段代码中括号的内容都是this，而method1和method2又是通过同一的对象去调用的，
// 所以进入同步块之前需要去竞争同一个对象上的锁，因此只能顺序执行同步块。






