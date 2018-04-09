package com.example.mac.back.data;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求体增加密文拦截器
 * Created by mac on 2018/4/4.
 */

public class RequestEncryptInterceptor implements Interceptor{

    private static final String FORM_NAME = "content";
    private static final String CHARSET = "UTF-8";


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request pre_request=chain.request();
        RequestBody pre_body=pre_request.body();


        if(pre_body instanceof FormBody){
            FormBody formBody=(FormBody) pre_body;

            Map<String, String> formMap = new HashMap<>();
            for (int i = 0; i < formBody.size(); i++) {
                formMap.put(formBody.name(i), formBody.value(i));
            }

            //拿到请求参数 转为json
            Gson gson = new Gson();
            String jsonParams = gson.toJson(formMap);

            //加密成密文

            //再组合成请求body
            pre_body = new FormBody.Builder().add(FORM_NAME, jsonParams).build();


            //再组合成请求体

            if (pre_body != null) {
                pre_request = pre_request.newBuilder()
                        .post(pre_body)
                        .build();
            }


            //交由下个拦截器
            return chain.proceed(pre_request);


        }





        return null;
    }
}
