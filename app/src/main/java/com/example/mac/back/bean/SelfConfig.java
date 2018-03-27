package com.example.mac.back.bean;

import java.io.Serializable;

/**
 * Created by mac on 2018/3/26.
 */

public class SelfConfig implements Serializable{



    private int position;
    private String content;
    private int drawble;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDrawble() {
        return drawble;
    }

    public void setDrawble(int drawble) {
        this.drawble = drawble;
    }

    @Override
    public String toString() {
        return "SelfConfig{" +
                "position=" + position +
                ", content='" + content + '\'' +
                ", drawble=" + drawble +
                '}';
    }
}
