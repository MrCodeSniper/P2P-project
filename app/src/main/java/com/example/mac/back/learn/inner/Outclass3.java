package com.example.mac.back.learn.inner;

/**
 * Created by mac on 2018/4/12.
 */

public class Outclass3 {

    private String name;
    private int age;

    public static class InnerStaticClass {

        private String name;

        public String getName() {
            return name;
        }

        public int getAge() {
            return new Outclass3().age;
        }
    }
}

class InnerClassTests {
    public static void main(String[] args) {
      new Outclass3.InnerStaticClass();
    }
}