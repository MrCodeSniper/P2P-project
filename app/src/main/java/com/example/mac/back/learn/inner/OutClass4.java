package com.example.mac.back.learn.inner;

/**
 * Created by mac on 2018/4/12.
 */

public class OutClass4 {

    private String className = "OutClass";

    {
        class PartClassOne { // 局部内部类
            private void method() {
                System.out.println("PartClassOne " + className);
            }
        }
        new PartClassOne().method();
    }

    public void testMethod() {
        class PartClassTwo { // 局部类内部类
            private void method() {
                System.out.println("PartClassTwo " + className);
            }
        }
        new PartClassTwo().method();
    }
}



class test{
    public static void main(String[] args) {
        OutClass4 outClass4=new OutClass4();
        outClass4.testMethod();
    }
}
