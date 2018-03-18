package com.example.mac.back.learn.builder;

/**
 * Created by mac on 2018/3/18.
 */

public class SonyBuilder implements Builder {

    private Product product = new Sony();

    @Override
    public void buildCPU() {
        product.add("CUP: Intel 酷睿i3 2350M");
    }

    @Override
    public void buildMemory() {
        product.add("内存: 4GB DDR3 1333MHz");
    }

    @Override
    public void buildGPU() {
        product.add("显卡: NVIDIA GeForce GT 520M");
    }

    @Override
    public Product getFinalProduct() {
        return product;
    }
}
