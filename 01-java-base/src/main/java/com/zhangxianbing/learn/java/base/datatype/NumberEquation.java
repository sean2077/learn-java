package com.zhangxianbing.learn.java.base.datatype;

/**
 * 知识点：
 * 1. 自动装箱和拆箱
 * 2. 包装类的equals()方法不处理数据转型
 * 3. 包装类的“==”运算在不遇到算术运算的情况下不会自动拆箱，否则会自动拆箱
 */
public class NumberEquation {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
        System.out.println(new Integer(2) == new Integer(2));
    }
    /*
     * 输出：               原因:
     * true                自动装箱,缓存
     * false               自动装箱,未缓存
     * true                包装类的“==”运算在遇到算术运算的情况下会自动拆箱
     * true                调用 equals(),比较的是值,而不是对象地址
     * true                包装类的“==”运算在遇到算术运算的情况下会自动拆箱
     * false               Long.equals: 将此对象与指定的对象进行比较, 当且仅当参数不为null并且是包含与此对象相同的long值的Long对象时，结果才为true 。
     * false               比较的是对象地址
     */
}
