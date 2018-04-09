package com.example.mac.back.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 每次请求头都加入token
 * Created by mac on 2018/4/4.
 */

public class TokenHeaderInterceptor implements Interceptor {

    String token="";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request pre_request=chain.request();//得到上个节点传来的请求信息

        Request updateRequest = pre_request.newBuilder()
                .header("token", token)
                .build();

        //加工请求的步骤
        //链子继续传
        return chain.proceed(updateRequest);





    }



}
