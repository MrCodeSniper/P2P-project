package com.example.mac.back.data;

/**
 * Created by mac on 2018/4/4.
 */

public class ConcreteHandlerA extends Handler {
    @Override
    public String handler(String condition) {
        if ("conditionA".equals(condition)){
            return "处理请求A";
        }else {
            System.out.println("转发请求A");
            return successor.handler(condition);
        }


    }
}

class ConcreteHandlerB extends Handler {
    public String handler(String condition) {
        if ("conditionB".equals(condition)){
            return "处理请求B";
        }else {
            System.out.println("转发请求B");
            return successor.handler(condition);
        }


    }
}

class ConcreteHandlerC extends Handler {
    public String handler(String condition) {
        if ("conditionC".equals(condition)){
            return "处理请求C";
        }else {
            System.out.println("转发请求C");
            return successor.handler(condition);

        }


    }
}


class ConcreteHandlerD extends Handler {
    public String handler(String condition) {
        if ("conditionD".equals(condition)){
            return "处理请求D";
        }else {
            System.out.println("转发请求D");
            return successor.handler(condition);
        }


    }
}

