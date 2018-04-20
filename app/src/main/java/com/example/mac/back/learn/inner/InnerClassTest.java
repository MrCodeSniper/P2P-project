package com.example.mac.back.learn.inner;

/**
 * 在控制台输出30，20，10
 * Created by mac on 2018/4/12.
 */
class Outer {
    public int num = 10;
    class Inner {
        public  int num = 20;//全局变量
        public  void show() {
             int num = 30;//局部变量
            System.out.println(num);    //填入合适的代码
            System.out.println(this.num);//当前内部类对象的sum this 表示当前的内部类对象
            System.out.println(Outer.this.num);//外部类对象的num  OutClass.this 表示当前外部类对象
        }
    }
}

public class InnerClassTest {
    public static void main(String[] args) {
        Outer.Inner oi = new Outer().new Inner();
        oi.show();

//        System.out.println( Outer.Inner.num);


    }
}
