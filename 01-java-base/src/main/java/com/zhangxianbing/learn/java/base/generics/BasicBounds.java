package com.zhangxianbing.learn.java.base.generics;

import java.awt.*;

/**
 * 比较下抽象类和接口
 * <p>
 * - 从设计层面上看，抽象类提供了一种 IS-A 关系，需要满足里式替换原则，即子类对象必须能够替换掉所有父类对象。而接口更像是一种 LIKE-A 关系，它只是提供一种方法实现契约，并不要求接口和实现接口的类具有 IS-A 关系。
 * - 从使用上来看，一个类可以实现多个接口，但是不能继承多个抽象类。
 * - 接口的字段只能是 static 和 final 类型的，而抽象类的字段没有这种限制。
 * - 接口的成员只能是 public 的，而抽象类的成员可以有多种访问权限。
 */
public class BasicBounds {
    public static void main(String[] args) {
        Solid<Bounded> solid = new Solid<>(new Bounded());
        solid.color();
        solid.weight();
        solid.getX();
    }
}

interface HasColor {
    Color getColor();
}

class Colored<T extends HasColor> {
    T item;

    Colored(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    Color color() {
        return item.getColor();
    }
}

class Dimension {
    public int x, y, z;
}

// extends中class必须放在interface之前
class ColoredDimension<T extends Dimension & HasColor> {
    T item;

    ColoredDimension(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    Color getColor() {
        return item.getColor();
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }
}

interface Weight {
    int weight();
}

/**
 * 通过继承，仅可继承一个具体的类，但可继承多个接口
 */
class Solid<T extends Dimension & HasColor & Weight> {
    T item;

    Solid(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    Color color() {
        return item.getColor();
    }

    int weight() {
        return item.weight();
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }
}

class Bounded extends Dimension implements HasColor, Weight {

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public int weight() {
        return 0;
    }
}
