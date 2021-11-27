package com.zhangxianbing.learn.java.base.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * PECS 总结：
 * <p>
 * - 要从泛型类取数据时，用 extends；
 * - 要往泛型类写数据时，用 super；
 * - 既要取又要写，就不用通配符（即 extends 与 super 都不用）
 */
public class PECS {
    public static void main(String[] args) {
        // 要从泛型类取数据时，用 extends；
        List<? extends Fruit> first = new ArrayList<Apple>();
        first.add(null);
        Fruit f = first.get(0);

        // incompatible type
//        first.add(new Apple());
//        first.add(new Fruit());
//        first.add(new Object());

        // 要往泛型类写数据时，用 super；
        List<? super Fruit> second = new ArrayList<>();
        second.add(new Apple());
        second.add(new Fruit());

        // 既要取又要写，就不用通配符（即 extends 与 super 都不用）
        List third = new ArrayList<Apple>();
        third.add(new Apple());
        Apple a = (Apple) third.get(0);

    }
}
