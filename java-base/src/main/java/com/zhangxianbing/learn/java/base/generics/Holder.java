package com.zhangxianbing.learn.java.base.generics;

public class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    public static void main(String[] args) {
        Holder<Apple> apple = new Holder<>(new Apple());
        Apple d = apple.get();
        apple.set(d);

        // 泛型不是协变的
//        Holder<Fruit> fruit = apple;
        // 可以通过通配符实现协变
        Holder<? extends Fruit> fruit = apple;
        // fruit 在实例化时就捕获了类型 ? extends Fruit 作为T, 因此在编译器看来，fruit.getValue()返回的为 ? extends Fruit ， 即 Fruit 及其子类 均可
        Fruit p = fruit.get();
        d = (Apple) fruit.get();
        try {
            // Orange 属于Fruit子类，所以编译器能通过，但运行时将 fruit.getValue() 转换成 Orange 会抛出 ClassCastException
            Orange o = (Orange) fruit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // fruit 在实例化时就捕获了类型 ? extends Fruit 作为T， 因此下述调用setValue会失
//        fruit.setValue(new Apple());
//        fruit.setValue(new Fruit());

        System.out.println(fruit.equals(d));
    }
}
