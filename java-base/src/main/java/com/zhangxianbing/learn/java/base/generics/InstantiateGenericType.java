package com.zhangxianbing.learn.java.base.generics;

public class InstantiateGenericType {
    public static void main(String[] args) {
        ClassAsFadtory<Employee> fe = new ClassAsFadtory<>(Employee.class);
        System.out.println("ClassAsFactory<Employee> succeeded");

        try {
            // Integer 没有无参数构造函数，所以在此处会失败
            ClassAsFadtory<Integer> fi = new ClassAsFadtory<>(Integer.class);
        } catch (Exception e) {
            System.out.println("ClassAsFactory<Integer> failed");
        }
    }
}

class ClassAsFadtory<T> {
    T x;


    public ClassAsFadtory(Class<T> kind) {
        try {
            x = kind.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Employee {
}
