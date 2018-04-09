package com.example.mac.back.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mac on 2018/4/5.
 */

public class ProxyHandler implements InvocationHandler {

    private Object tar;//静态代理

    public Object bind(Object tar)
    {
        this.tar = tar;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }




    //绑定动态代理 执行接口函数 跳转invoke
    //h.invoke(this, m3, null);
    //method 是通过反射 拿到类的doSomething方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //这里就可以进行所谓的AOP编程了
        //在调用具体函数方法前，执行功能处理
        System.out.println("start time:"+System.currentTimeMillis());
        result = method.invoke(tar,args);//调用静态代理的doSomething方法
        //在调用具体函数方法后，执行功能处理
        System.out.println("end time:"+System.currentTimeMillis());
        return result;
    }




}
