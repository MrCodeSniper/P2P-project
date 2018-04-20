package com.example.mac.back.learn.inner;

/**
 * Created by mac on 2018/4/12.
 */
interface Inter {
    void show();
}
class Outers {

    private Inter inter;

    //补齐代码
     public static Inter method(){
         return new Inter() {
             @Override
             public void show() {
                 System.out.println("HelloWorld");
             }
         };
     }

    public Outers setClickListener(Inter inter) {
        this.inter=inter;
        return this;
    }

    public void setInter(String names){
         setClickListener(new Inter() {
             @Override
             public void show() {
                 System.out.println("click " + names);
             }
         });
    }

}
public class OuterDemo {
    public static void main(String[] args) {
        Outers.method().show();
    }
}
