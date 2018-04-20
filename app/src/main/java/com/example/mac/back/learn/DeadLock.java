package com.example.mac.back.learn;

/**
 * Created by mac on 2018/4/11.
 */




    class Thread01 extends Thread{
        private Object resource01;
        private Object resource02;
        public Thread01(Object resource01, Object resource02) {
            this.resource01 = resource01;
            this.resource02 = resource02;
        }
        @Override
        public void run() {
            synchronized(resource01){
                System.out.println("Thread01 locked resource01");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource02) {
                    System.out.println("Thread01 locked resource02");
                }
            }
        }
    }
    class Thread02 extends Thread{
        private Object resource01;
        private Object resource02;
        public Thread02(Object resource01, Object resource02) {
            this.resource01 = resource01;
            this.resource02 = resource02;

        }
        @Override
        public void run() {
            synchronized(resource02){
                System.out.println("Thread02 locked resource02");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource01) {
                    System.out.println("Thread02 locked resource01");
                }
            }
        }
    }



public class DeadLock {
    public static void main(String[] args) {
        final Object resource01 = "resource01";
        final Object resource02 = "resource02";
        Thread01 thread01 = new Thread01(resource01, resource02);
        Thread02 thread02 = new Thread02(resource01, resource02);
        thread01.start();
        thread02.start();
    }
}

