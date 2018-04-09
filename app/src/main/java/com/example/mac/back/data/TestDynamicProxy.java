package com.example.mac.back.data;

/**
 * Created by mac on 2018/4/5.
 */

public class TestDynamicProxy {

    public static void main(String args[]){
//        Subject sub = new RealSubject();
        ProxyHandler proxy = new ProxyHandler();


        //绑定该类实现的所有接口
        Subject sub = (Subject) proxy.bind(new RealSubject());

        /*
         * 获取代理类。
         * Class<?> cl = getProxyClass0(loader, intfs);
         * 中return proxyClassCache.get(loader, interfaces);
         * 中 return supplier.get()；
         *中 return value = Objects.requireNonNull(valueFactory.apply(key, parameter));
         *  byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);
            return defineClass0(loader, proxyName,proxyClassFile, 0, proxyClassFile.length);

         *  final byte[] classFile = gen.generateClassFile();生成字节码文件
         */



        //调用了return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);






        //return cons.newInstance(new Object[]{h});
        // 生成代理类的实例，并把MyInvocationHander的实例作为构造函数参数传入




        //绑定静态代理
        sub.doSomething();//invoke
    }


}
