package com.zhangxianbing.learn.java.base.generics;

public class GenericMethods {
    public static <T> void f(T x) {
        System.out.println(x.getClass());
    }

    public static void main(String[] args) {
        f("");
        f(1);
        f(1.0);
        f(1L);
        f('c');
        f(new GenericMethods());
    }
}
