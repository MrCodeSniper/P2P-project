package com.example.mac.back.utils;

/**
 * Created by mac on 2018/3/21.
 */

public class RemoveHZ {

    public static String deal(String s){
        StringBuffer sb=new StringBuffer(s);
        StringBuffer se=new StringBuffer();
        int l=sb.length();//统计汉字与英语相加的个数
        char c;
        for(int i=0;i<l;i++){
            c=sb.charAt(i);
            if(c>40&&c<127){
                se.append(c);
            }
        }
        return new String(se);
    }
}
