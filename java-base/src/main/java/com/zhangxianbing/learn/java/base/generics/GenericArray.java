package com.zhangxianbing.learn.java.base.generics;

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

        // ClassCastException
        try {
            Integer[] ia = gai.rep();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[] oa = gai.rep();
    }
}
