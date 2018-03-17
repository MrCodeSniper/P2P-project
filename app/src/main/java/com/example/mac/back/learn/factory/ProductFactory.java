package com.example.mac.back.learn.factory;

/**
 * Created by mac on 2018/3/17.
 */
public class ProductFactory {
    public static Product produce(String productName) throws Exception {
        switch (productName) {
            case "tv":
                return new Tv();
            case "car":
                return new Car();
            default:
                throw new Exception("没有该产品");
        }
    }
}

