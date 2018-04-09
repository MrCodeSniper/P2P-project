package com.example.mac.back.data;

import retrofit2.Retrofit;

/**
 * Created by mac on 2018/4/4.
 */

public class Client {

    public static void main(String[] args) {
        Handler concreteHandlerA=new ConcreteHandlerA();
        Handler concreteHandlerB=new ConcreteHandlerB();
        Handler concreteHandlerC=new ConcreteHandlerC();
        Handler concreteHandlerD=new ConcreteHandlerD();


        //创建所有具体处理者
        concreteHandlerA.setSuccessor(concreteHandlerB);
        concreteHandlerB.setSuccessor(concreteHandlerC);
        concreteHandlerC.setSuccessor(concreteHandlerD);


        String s=concreteHandlerA.handler("conditionD");
        System.out.println(s);


        Retrofit.Builder builder;


    }


}
