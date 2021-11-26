package com.zhangxianbing.learn.java.algorithm.bitops;

public class Binary {
    public static void main(String[] args) {
        int i = 5;
        int j = 10;
        System.out.println(i + ~j);

        int[] arr = new int[]{3, -2};
        for (int a : arr) {
            // 原数
            System.out.println("a:" + a + "  二进制:" + Integer.toBinaryString(a));
            // 按位取反
            System.out.println("~a:" + ~a + "  二进制:" + Integer.toBinaryString(~a));
            // 相反数
            System.out.println("-a:" + -a + "  二进制:" + Integer.toBinaryString(-a));

            System.out.println(-a == ~a + 1);
            System.out.println(~a == -a - 1);
        }
    }
}
