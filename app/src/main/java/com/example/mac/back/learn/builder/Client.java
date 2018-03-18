package com.example.mac.back.learn.builder;

/**
 * Created by mac on 2018/3/18.
 */

public class Client {

        private static Builder sonyBuilder=new SonyBuilder();//builder用来生产整体实例



    public void produce(){



        Direcotr direcotr =new Direcotr(sonyBuilder);//创建子部件 并依赖于父部件 在父部件内部了
        direcotr.construct();

        Product product=sonyBuilder.getFinalProduct();//父部件返回自身


        product.show();







    }







}
