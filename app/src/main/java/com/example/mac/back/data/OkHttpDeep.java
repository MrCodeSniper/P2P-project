package com.example.mac.back.data;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mac on 2018/4/4.
 */

public class OkHttpDeep {

    private OkHttpClient client;

    public OkHttpDeep(OkHttpClient client) {
        this.client=client;
    }

    public Response get(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        //根据url生成http request对象
        //用到建造者模式
        //进行装配零件
        try {
            return client.newCall(request).execute();
            //进入new RealCall(this, request);构建realcall
            //将client和request赋值初始化
            //再看execute
            //同步操作
            //
            /*
            * client.dispatcher().executed(this);内部
            * runningSyncCalls.add(call); 将当前call加入到运行中的Realcall队列中 执行完毕在队列中移除
            *核心操作
            * Response result = getResponseWithInterceptorChain(false);得到响应数据
            * 使用拦截器链来获取响应数据
            *
            * 在这个方法中加入大量的拦截器
            * RealInterceptorChain链式调用拦截器
            *
            * Interceptor.Chain chain = new ApplicationInterceptorChain(index + 1, request, forWebSocket);
            * 得到下一次对应的链
            * Interceptor interceptor = client.interceptors().get(index);
            * 当前的拦截器
            *
            *进行拦截处理，并且在 interceptor 链式调用 next 的 proceed 方法
              Response response = interceptor.intercept(next);
            *
            * 交由下个拦截器处理 直至所有拦截器都处理完毕
            *
            * 每个拦截器进入先拿到chain中的request
            * 若有请求体，则构造
            * 加入builder装配一些东西
            * 之后交由下一个拦截器
            * Response networkResponse = chain.proceed(requestBuilder.build());
            *
            * 将下一个拦截器返回的response构建完毕返回
            *
            * 即每个拦截器都装配respose
            *
            *
            *
            *
            * */





        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void main(String[] args){
        OkHttpClient client=new OkHttpClient();
        //调用OkHttpClient(Builder builder) 进行装配配置默认参数
        //构造器基本上都是扎堆的配置








    }


}
