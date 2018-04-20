package com.example.mac.back.learn;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.mac.back.learn.collection.ColorData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/4/6.
 */

public class Tesst {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args){
//        Test test=new Test("helloworld");
//        test.printSum(1,2);

        List<ColorData> myShapesCollection=new ArrayList<ColorData>();

        myShapesCollection.add(new ColorData("xxx",Color.RED));

        //lambda表达式
        myShapesCollection.stream()
                .filter(e -> e.getColor() == Color.RED)
                .forEach(e -> System.out.println(e.getData()));
    }

}
