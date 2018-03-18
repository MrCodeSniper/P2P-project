package com.example.mac.back.learn.builder;

/**
 * Created by mac on 2018/3/18.
 */

public class Direcotr {

    //负责生产配件 并进行拼接



    private Builder builder;


    public Direcotr(Builder builder) {
        this.builder = builder;
    }



    public void  construct(){

        builder.buildCPU();
        builder.buildGPU();
        builder.buildMemory();


    }



}
