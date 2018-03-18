package com.example.mac.back.learn.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/3/18.
 */

public abstract  class Product {

    protected List<String> parts = new ArrayList<String>(); //部件列表
    public void add(String part){
        parts.add(part);
    }
    //显示产品信息

    public void show(){
        System.out.print("产品部件信息：");
        for(String part : parts){
            System.out.print(part + "\t");
        }
    }



}
