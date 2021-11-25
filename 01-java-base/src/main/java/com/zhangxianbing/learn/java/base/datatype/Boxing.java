package com.zhangxianbing.learn.java.base.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识点：
 * 1. 装箱与拆箱
 * <p>
 * 自动装箱是 Java 编译器在原始类型与其对应的对象包装类之间进行的自动转换。例如，将int转换为Integer，将double转换为Double，等等。(是否可以理解为C++里的隐式转换？)如果转换以另一种方式进行，则称为unboxing。
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html">Autoboxing and Unboxing</a>
 */
public class Boxing {
    public static void main(String[] args) {
        Integer x = 2;     // 装箱 调用了 Integer.valueOf(2)
        int y = x;         // 拆箱 调用了 X.intValue()

        Integer i = new Integer(-8);

        // 1. 通过方法调用拆箱
        int absVal = absoluteValue(i);
        System.out.println("" + i + " = " + absVal);

        List<Double> ld = new ArrayList<>();
        ld.add(3.1416); // Π 通过方法调用自动装箱。

        // 2. 通过赋值进行拆箱
        double pi = ld.get(0);
        System.out.println("pi = " + pi);
    }


    public static int absoluteValue(int i) {
        return (i < 0) ? -i : i;
    }
}
