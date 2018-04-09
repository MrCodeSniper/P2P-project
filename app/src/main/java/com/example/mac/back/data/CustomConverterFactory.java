package com.example.mac.back.data;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * 自定义的json转bean来处理 200 和400时不同的json格式
 * Created by mac on 2018/4/4.
 */

public class CustomConverterFactory extends Converter.Factory {


    private final Gson gson;

    public static CustomConverterFactory create() {
        return create(new Gson());
    }

    private CustomConverterFactory(Gson gson) {
        this.gson = gson;
    }


    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static CustomConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new CustomConverterFactory(gson);
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        // attention here!
        return new CustomResponseConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new GsonRequestBodyConverter<>(gson, adapter);
        return null;
    }




}
