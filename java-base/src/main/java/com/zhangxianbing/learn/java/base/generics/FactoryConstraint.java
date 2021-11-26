package com.zhangxianbing.learn.java.base.generics;

public class FactoryConstraint {
    public static void main(String[] args) {
        new Foo<Integer>(new IntegerFactory());
        new Foo<Widget>(new Widget.Factory());
    }
}

interface FactoryInterface<T> {
    T create();
}

class Foo<T> {
    private T x;

    public <F extends FactoryInterface<T>> Foo(F factory) {
        x = factory.create();
    }
}

class IntegerFactory implements FactoryInterface<Integer> {

    @Override
    public Integer create() {
        return 0;
    }
}

class Widget {
    public static class Factory implements FactoryInterface<Widget> {
        @Override
        public Widget create() {
            return new Widget();
        }
    }
}
