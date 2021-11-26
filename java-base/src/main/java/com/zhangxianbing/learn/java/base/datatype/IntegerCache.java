package com.zhangxianbing.learn.java.base.datatype;

/**
 * 知识点:
 * 1. 缓存池
 * 2. 自动装箱
 *
 * @see
 * <a href="https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%9F%BA%E7%A1%80.md#%E7%BC%93%E5%AD%98%E6%B1%A0">缓存池</a>
 */
public class IntegerCache {
    public static void main(String[] args) {
        Integer x = new Integer(123);
        Integer y = new Integer(123);
        System.out.println(x == y);    // false: new Integer(123) 每次都会新建一个对象
        Integer z = Integer.valueOf(123);
        Integer k = Integer.valueOf(123);
        System.out.println(z == k);   // true: Integer.valueOf(123) 会使用缓存池中的对象，多次调用会取得同一个对象的引用
        Integer m = 123;
        Integer n = 123;
        System.out.println(m == n); // true: 编译器会在自动装箱过程调用 valueOf() 方法，因此多个值相同且值在缓存池范围内的 Integer
        // 实例使用自动装箱来创建，那么就会引用相同的对象。
    }
/*
valueOf() 方法的实现: 先判断值是否在缓存池中，如果在的话就直接返回缓存池的内容。
在 Java 8 中，基本类型对应的缓冲池如下：

boolean values true and false
all byte values
short values between -128 and 127
int values between -128 and 127
char in the range \u0000 to \u007F

在 jdk 1.8 所有的数值类缓冲池中，Integer 的缓冲池 IntegerCache 很特殊，这个缓冲池的下界是 - 128，上界默认是 127，但是这个上界是可调的，在启动 jvm 的时候，通过
-XX:AutoBoxCacheMax=<size> 来指定这个缓冲池的大小，该选项在 JVM 初始化的时候会设定一个名为 java.lang.IntegerCache.high 系统属性，然后 IntegerCache
初始化的时候就会读取该系统属性来决定上界。
 */
}
