package com.zhangxianbing.learn.java.base.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericWriting {
    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }

    static List<Apple> apples = new ArrayList<>();
    static List<Fruit> fruits = new ArrayList<>();

    static void f1() {
        writeExact(apples, new Apple());
        //  根据里氏替换原则，子类对象是可以替换父类对象
        writeExact(apples, new RedApple());

        //  根据里氏替换原则，子类对象是可以替换父类对象
        writeExact(fruits, new Apple());
    }

    // 貌似和 writeExact 无差别？
    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }

    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruits, new Apple());
    }

    static class Writer<T> {
        void writeExact(List<T> list, T item) {
            list.add(item);
        }
    }

    static void f3() {
        Writer<Fruit> writer = new Writer<>();
        writer.writeExact(fruits, new Fruit());
        writer.writeExact(fruits, new Apple());
        // 编译不通过
//        writer.writeExact(apples, new Apple());
    }

    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }
}
