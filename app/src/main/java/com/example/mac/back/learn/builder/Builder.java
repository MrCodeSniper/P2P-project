package com.example.mac.back.learn.builder;

/**
 * Created by mac on 2018/3/18.
 */

public interface Builder  {

    //生产接口 包含生产部件和成品

    public void buildCPU();

    public void buildMemory();

    public void buildGPU();

    public Product getFinalProduct();










}
