package com.zhangxianbing.learn.java.algorithm.bitops;

import java.util.HashMap;

public class PowerOfTwo {
    /**
     * 返回给定目标容量的二次幂.
     */
    static int tableSizeFor(int cap) {
        HashMap<Integer, Integer> m;
        int n = cap - 1;
        System.out.println(Integer.toBinaryString(n) + " | " + Integer.toBinaryString(n >>> 1));
        // 此步能保证右侧min(2,m)位一定为1, m为cap-1不为0的最高位数
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n) + " | " + Integer.toBinaryString(n >>> 2));
        // 此步能保证右侧min(4,m)位一定为1, m为cap-1不为0的最高位数
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n) + " | " + Integer.toBinaryString(n >>> 4));
        // 此步能保证右侧min(8,m)位一定为1, m为cap-1不为0的最高位数
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n) + " | " + Integer.toBinaryString(n >>> 8));
        // 此步能保证右侧min(16,m)位一定为1, m为cap-1不为0的最高位数
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n) + " | " + Integer.toBinaryString(n >>> 16));
        // 此步能保证右侧min(32,m)位一定为1, m为cap-1不为0的最高位数
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n));
        return (n < 0) ? 1 : n + 1;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1026; i++) {
            System.out.println("---");
            System.out.println(i + " " + tableSizeFor(i));
        }
    }
}
