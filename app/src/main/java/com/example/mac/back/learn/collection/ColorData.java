package com.example.mac.back.learn.collection;

/**
 * Created by mac on 2018/4/12.
 */

public class ColorData {

    private String data;

    private int color;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ColorData(String data, int color) {
        this.data = data;
        this.color = color;
    }
}
