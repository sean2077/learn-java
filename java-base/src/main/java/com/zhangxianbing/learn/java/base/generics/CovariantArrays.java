package com.zhangxianbing.learn.java.base.generics;

/**
 * 协变与逆变  https://www.cnblogs.com/en-heng/p/5041124.html 泛型是不变的 数组是协变的
 * <p>
 * 在Java中，Object[]数组可以是任何数组的父类，或者说，任何一个数组都可以向上转型成它在定义时指定元素类型的父类的数组，这个时候如果我们往里面放不同于原始数据类型
 * 但是满足后来使用的父类类型的话，编译不会有问题，但是在运行时会检查加入数组的对象的类型，于是会抛ArrayStoreException：
 * <p>
 * 作者：ylxfc 链接：https://www.zhihu.com/question/20928981/answer/39234969 来源：知乎 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();
        fruits[1] = new RedApple();

        // 编译器允许添加 Fruit，但运行时会抛出 ArrayStoreException
        // 为了确保类型安全，Java数组必须明确知道内部元素的类型，而且会”记住“这个类型，每次往数组里插入新元素都会进行类型检查，不匹配会抛出java.lang.ArrayStoreException。
        try {
            fruits[2] = new Fruit();
        } catch (Exception e) {
            System.out.println(e);
        }

        // 编译器允许添加 Orange，但运行时会抛出 ArrayStoreException
        try {
            fruits[3] = new Orange();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class Fruit {
}

class Apple extends Fruit {
}

class RedApple extends Apple {
}

class Orange extends Fruit {
}
