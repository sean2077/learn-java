package com.zhangxianbing.learn.java.base.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * [分析一下为什么JAVA不支持泛型类型的数组](https://www.cnblogs.com/scutwang/articles/3735219.html)
 *
 * @param <T>
 */
public class GenericArray<T> {
    private T[] array;

    public GenericArray(int sz) {
        array = (T[]) new Object[sz];
    }

    public void put(int i, T item) {
        array[i] = item;
    }

    public T get(int i) {
        return array[i];
    }

    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArray<Integer> gai = new GenericArray<>(10);


        try {
            Integer[] ia = gai.rep();
        } catch (Exception e) {
            e.printStackTrace();
            // java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class [Ljava.lang.Integer;
            // ([Ljava.lang.Object; and [Ljava.lang.Integer; are in module java.base of loader 'bootstrap')
            //	at com.zhangxianbing.learn.java.base.generics.GenericArray.main(GenericArray.java:32)
            // 泛型在运行时已经擦除了类型参数，因此 gai.rep() 实际返回的是 Object[], 无法直接赋值给 Integer[] 类型
        }
        Object[] oa = gai.rep();
        List l = new ArrayList();
        l.add(1);
        l.add("str");
    }
}
