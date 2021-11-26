package com.zhangxianbing.learn.java.base.generics;

import java.util.ArrayList;

/**
 * 不同于 C++ 的模板机制，**Java 泛型是使用类型擦除来实现的，使用泛型时，任何具体的类型信息都被擦除了**。（如在代码中定义`List<Object>`和`List<String>`等类型，在编译后都会变成`List
 * `，JVM 看到的只是`List`）
 */
public class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        Class c3 = ArrayList.class;
        System.out.println(c1 == c2);
        System.out.println(c1 == c3);
    }
}
/* Output:
true
true
 */
