package com.zhangxianbing.learn.java.base;

public class Binary {
  public static void main(String[] args) {
    int i = 5;
    int j = 10;
    System.out.println(i + ~j);

    int[] arr = new int[] {3, -2};
    for (int a : arr) {
      System.out.println(a);
      System.out.println(~a);
      System.out.println(-a);
      System.out.println(-a == ~a + 1);
      System.out.println(~a == -a - 1);
    }
  }
}
