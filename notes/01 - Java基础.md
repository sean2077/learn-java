# Java 基础

## 面向对象

### 面向对象三大特征

封装、继承、多态

### 面向对象的五大基本原则

- 单一职责原则(SRP): 一个类，最好只做一件事，只有一个引起它的变化。
- 开放封闭原则(OCP): 软件实体应该是可扩展的，而不可修改的
- 里氏替换原则(LSP): 子类必须能够替换其基类
- 依赖倒置原则(DIP): 依赖于抽象。具体而言就是高层模块不依赖于底层模块，二者都同依赖于抽象；抽象不依赖于具体，具体依赖于抽象
- 接口隔离原则: 使用多个小的专门的接口，而不要使用一个大的总接口

### 访问权限

Java 中，可以使用访问控制符来保护对类、变量、方法和构造方法的访问。Java 支持 4 种不同的访问权限。

- **default** (即默认，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。
- **private** : 在同一类内可见。使用对象：变量、方法。 **注意：不能修饰类（外部类）**
- **public** : 对所有类可见。使用对象：类、接口、变量、方法
- **protected** : 对同一包内的类和所有子类可见。使用对象：变量、方法。 **注意：不能修饰类（外部类）**。

我们可以通过以下表来说明访问权限：

| 修饰符      | 当前类 | 同一包内 | 子孙类(同一包) | 子孙类(不同包)                                                                     | 其他包 |
| :---------- | :----- | :------- | :------------- | :--------------------------------------------------------------------------------- | :----- |
| `public`    | Y      | Y        | Y              | Y                                                                                  | Y      |
| `protected` | Y      | Y        | Y              | Y/N（[说明](https://www.runoob.com/java/java-modifier-types.html#protected-desc)） | N      |
| `default`   | Y      | Y        | Y              | N                                                                                  | N      |
| `private`   | Y      | N        | N              | N                                                                                  | N      |

可以对类或类中的成员（字段和方法）加上访问修饰符。

- 类可见表示其它类可以用这个类创建实例对象。
- 成员可见表示其它类可以用这个类的实例对象访问到该成员；

protected 用于修饰成员，表示在继承体系中成员对于子类可见，但是这个访问修饰符对于类没有意义。

如果子类的方法重写了父类的方法，那么子类中该方法的访问级别不允许低于父类的访问级别。这是为了确保可以使用父类实例的地方都可以使用子类实例去代替，也就是确保满足里氏替换原则。

字段决不能是公有的，因为这么做的话就失去了对这个字段修改行为的控制，客户端可以对其随意修改。例如下面的例子中，AccessExample 拥有 id 公有字段，如果在某个时刻，我们想要使用 int 存储 id 字段，那么就需要修改所有的客户端代码。

可以使用公有的 getter 和 setter 方法来替换公有字段，这样的话就可以控制对字段的修改行为。

但是也有例外，如果是包级私有的类或者私有的嵌套类，那么直接暴露成员不会有特别大的影响。

### 比较下抽象类和接口

- 从设计层面上看，抽象类提供了一种 IS-A 关系，需要满足里式替换原则，即子类对象必须能够替换掉所有父类对象。而接口更像是一种 LIKE-A 关系，它只是提供一种方法实现契约，并不要求接口和实现接口的类具有 IS-A 关系。
- 从使用上来看，一个类可以实现多个接口，但是不能继承多个抽象类。
- 接口的字段只能是 static 和 final 类型的，而抽象类的字段没有这种限制。
- 接口的成员只能是 public 的，而抽象类的成员可以有多种访问权限。

### 何时使用抽象类，何时使用接口

使用接口：

- 需要让不相关的类都实现一个方法，例如不相关的类都可以实现 Comparable 接口中的 compareTo() 方法；
- 需要使用多重继承。

使用抽象类：

- 需要在几个相关的类中共享代码。
- 需要能控制继承来的成员的访问权限，而不是都为 public。
- 需要继承非静态和非常量字段。

在很多情况下，接口优先于抽象类。因为接口没有抽象类严格的类层次结构要求，可以灵活地为一个类添加行为。并且从 Java 8 开始，接口也可以有默认的方法实现，使得修改接口的成本也变的很低。

### 重载和重写的区别

> 重载就是同样的一个方法能够根据输入数据的不同，做出不同的处理
>
> 重写就是当子类继承自父类的相同方法，输入数据一样，但要做出有别于父类的响应时，你就要覆盖父类方法

**重载**

发生在同一个类中（或者父类和子类之间），方法名必须相同，参数类型不同、个数不同、顺序不同，方法返回值和访问修饰符可以不同。

综上：重载就是同一个类中多个同名方法根据不同的传参来执行不同的逻辑处理。

**重写**

重写发生在运行期，是子类对父类的允许访问的方法的实现过程进行重新编写。

1. 返回值类型、方法名、参数列表必须相同，抛出的异常范围小于等于父类，访问修饰符范围大于等于父类。
2. 如果父类方法访问修饰符为 `private/final/static` 则子类就不能重写该方法，但是被 static 修饰的方法能够被再次声明。
3. 构造方法无法被重写

综上：**重写就是子类对父类方法的重新改造，外部样子不能改变，内部逻辑可以改变。**

| 区别点     | 重载方法 | 重写方法                                                         |
| :--------- | :------- | :--------------------------------------------------------------- |
| 发生范围   | 同一个类 | 子类                                                             |
| 参数列表   | 必须修改 | 一定不能修改                                                     |
| 返回类型   | 可修改   | 子类方法返回值类型应比父类方法返回值类型更小或相等               |
| 异常       | 可修改   | 子类方法声明抛出的异常类应比父类方法声明抛出的异常类更小或相等； |
| 访问修饰符 | 可修改   | 一定不能做更严格的限制（可以降低限制）                           |
| 发生阶段   | 编译期   | 运行期                                                           |

**方法的重写要遵循“两同两小一大”**（以下内容摘录自《疯狂 Java 讲义》，[issue#892 (opens new window)](https://github.com/Snailclimb/JavaGuide/issues/892) ）：

- “两同”即方法名相同、形参列表相同；
- “两小”指的是子类方法返回值类型应比父类方法返回值类型更小或相等，子类方法声明抛出的异常类应比父类方法声明抛出的异常类更小或相等；
- “一大”指的是子类方法的访问权限应比父类方法的访问权限更大或相等。

⭐️ 关于 **重写的返回值类型** 这里需要额外多说明一下，上面的表述不太清晰准确：如果方法的返回类型是 void 和基本数据类型，则返回值重写时不可修改。但是如果方法的返回值是引用类型，重写时是可以返回该引用类型的子类的。

```java
public class Hero {
    public String name() {
        return "超级英雄";
    }
}
public class SuperMan extends Hero{
    @Override
    public String name() {
        return "超人";
    }
    public Hero hero() {
        return new Hero();
    }
}

public class SuperSuperMan extends SuperMan {
    public String name() {
        return "超级超级英雄";
    }

    @Override
    public SuperMan hero() {
        return new SuperMan();
    }
}
```

## 数据结构

### [HashMap 容量为什么总是为 2 的次幂？](https://www.cnblogs.com/javastack/p/13356402.html)

HashMap 是根据 key 的 hash 值决策 key 放入到哪个桶（bucket）中，通过 tab[(n - 1) & hash] 公式计算得出，其中 tab 是一个哈希表。

**为什么要通过 (n - 1) & hash 决定桶的索引呢？**

- &运算速度快，至少比%取模运算块

- (n - 1) & hash，当 n 为 2 次幂时，会满足一个公式：(n - 1) & hash = hash % n

![image-20211126151441769](https://gitee.com/zhangxianbing/assets/raw/master/typora/2021-11-26-15-14-49_image-20211126151441769.png)

## 泛型

- [深入理解 Java 泛型](https://dunwu.github.io/javacore/basics/java-generic.html)
- [Java 泛型详解](https://www.cnblogs.com/Blue-Keroro/p/8875898.html)
- <https://www.cnblogs.com/wuqinglong/p/9456193.html>
- [10 道 Java 泛型面试题](https://cloud.tencent.com/developer/article/1033693)

### Java 中的泛型是什么 ? 使用泛型的好处是什么?

**泛型，即“参数化类型”**

好处：

- 提供了编译期的类型安全，避免运行时出现 ClassCastException。
- 可以避免类型转换
- 泛型编程可实现通用算法

### 类型参数命名原则

Java 编程约定要求类型参数名称为单个大写字母，例如 E 表示元素，K 表示键，V 表示值，T 表示类型。避免使用像 A, B, C 这样没有意义的名称。(但并不是语法层面的限制)。

### Java 的泛型是如何工作的 ? 什么是类型擦除 ?

不同于 C++ 的模板机制，**Java 泛型是使用类型擦除来实现的，使用泛型时，任何具体的类型信息都被擦除了**。（如在代码中定义`List<Object>`和`List<String>`等类型，在编译后都会变成`List`，JVM 看到的只是`List`）

拓展：C++模板机制：编译器从函数模板通过具体类型产生不同的函数；编译器会对函数模板进行两次编译：在声明的地方对模板代码本身进行编译，在调用的地方对参数替换后的代码进行编译。

类型擦除做了什么呢？它做了以下工作：

- 把泛型中的所有类型参数替换为 Object，如果指定类型边界，则使用类型边界来替换。因此，生成的字节码仅包含普通的类，接口和方法。
- 擦除出现的类型声明，即去掉 `<>` 的内容。比如 `T get()` 方法声明就变成了 `Object get()` ；`List<String>` 就变成了 `List`。如有必要，插入类型转换以保持类型安全。
- 生成桥接方法以保留扩展泛型类型中的多态性。类型擦除确保不为参数化类型创建新类；因此，泛型不会产生运行时开销。 [类型擦除与多态的冲突和解决方法](https://www.cnblogs.com/wuqinglong/p/9456193.html)

例子：

```java
List<String> stringArrayList = new ArrayList<String>();
List<Integer> integerArrayList = new ArrayList<Integer>();

Class classStringArrayList = stringArrayList.getClass();
Class classIntegerArrayList = integerArrayList.getClass();

if(classStringArrayList.equals(classIntegerArrayList)){
    Log.d("泛型测试","类型相同");
}
```

输出结果：`泛型测试: 类型相同`。

**总结成一句话：泛型类型在逻辑上看以看成是多个不同的类型，实际上都是相同的基本类型。**

### 泛型的使用

- 在泛型使用过程中，操作的数据类型被指定为一个参数，这种参数类型可以用在类、接口和方法中，分别被称为泛型类、泛型接口、泛型方法。

- 定义的泛型类，就一定要传入泛型类型实参么？可以不传

  在使用泛型的时候如果传入泛型实参，则会根据传入的泛型实参做相应的限制，此时泛型才会起到本应起到的限制作用。

  如果不传入泛型类型实参的话，在泛型类中使用泛型的方法或成员变量定义的类型可以为任何的类型，但这样的调用就失去泛型类型的优势。

### 泛型与继承

**泛型不能用于显式地引用运行时类型的操作之中，例如：转型、instanceof 操作和 new 表达式。因为所有关于参数的类型信息都丢失了**。当你在编写泛型代码时，必须时刻提醒自己，你只是看起来好像拥有有关参数的类型信息而已。

正是由于泛型时基于类型擦除实现的，所以，**泛型类型无法向上转型**。

> 向上转型是指用子类实例去初始化父类，这是面向对象中多态的重要表现。

![img](https://raw.githubusercontent.com/dunwu/images/dev/snap/1553147778883.png)

`Integer` 继承了 `Object`；`ArrayList` 继承了 `List`；但是 `List<Interger>` 却并非继承了 `List<Object>`。

这是因为，泛型类并没有自己独有的 `Class` 类对象。比如：并不存在 `List<Object>.class` 或是 `List<Interger>.class`，Java 编译器会将二者都视为 `List.class`。

相关问题：

1. 可以把 List<String>传递给一个接受 List<Object>参数的方法吗？不行

### 什么是泛型中的限定通配符和非限定通配符 ?

- 类型通配符一般是使用？代替具体的类型实参，注意，**此处’？’是类型实参，而不是类型形参** 。（可以把？看成所有类型的父类。是一种真实的类型。）
- 限定通配符对类型进行了限制。有两种限定通配符，一种是<? extends T>它通过确保类型必须是 T 的子类来设定类型的**上界**，另一种是<? super T>它通过确保类型必须是 T 的父类来设定类型的**下界**。泛型类型必须用限定内的类型来进行初始化，否则会导致编译错误。
- <?>表示了非限定通配符，因为<?>可以用任意类型来替代。

### 通配符和向上转型

前面，我们提到：**泛型不能向上转型。但是，我们可以通过使用通配符来向上转型**。

### 协变与逆变

<https://www.cnblogs.com/en-heng/p/5041124.html>

逆变与协变用来描述类型转换（type transformation）后的继承关系，其定义：如果*𝐴*、*𝐵*表示类型，_𝑓_(⋅)f(⋅)表示类型转换，≤≤ 表示继承关系（比如，*𝐴*≤*𝐵*表示*𝐴*是由*𝐵*派生出来的子类）；

- _𝑓_(⋅)f(⋅)是逆变（contravariant）的，当*𝐴*≤*𝐵 时有*𝑓*(*𝐵*)≤*𝑓*(*𝐴)成立；
- _𝑓_(⋅)f(⋅)是协变（covariant）的，当*𝐴*≤*𝐵 时有*𝑓*(*𝐴*)≤*𝑓*(*𝐵)成立；
- _𝑓_(⋅)f(⋅)是不变（invariant）的，当*𝐴*≤*𝐵 时上述两个式子均不成立，即*𝑓*(*𝐴*)与*𝑓(𝐵)相互之间没有继承关系。

数组是协变的

```java
Number[] numbers = new Integer[3];
```

Java 中泛型是不变的，可有时需要实现逆变与协变，怎么办呢？这时，通配符`?`派上了用场：

- `<? extends>`实现了泛型的协变，比如：

```java
List<? extends Number> list = new ArrayList<Integer>();
```

- `<? super>`实现了泛型的逆变，比如：

```java
List<? super Number> list = new ArrayList<Object>();
```

现在问题来了：究竟什么时候用 extends 什么时候用 super 呢？《Effective Java》给出了答案：

> PECS: producer-extends, consumer-super.

比如，一个简单的 Stack API：

```java
public class  Stack<E>{
    public Stack();
    public void push(E e):
    public E pop();
    public boolean isEmpty();
}
```

要实现`pushAll(Iterable<E> src)`方法，将 src 的元素逐一入栈：

```java
public void pushAll(Iterable<E> src){
    for(E e : src)
        push(e)
}
```

假设有一个实例化`Stack<Number>`的对象 stack，src 有`Iterable<Integer>`与 `Iterable<Float>`；在调用 pushAll 方法时会发生 type mismatch 错误，因为 Java 中泛型是不可变的，`Iterable<Integer>`与 `Iterable<Float>`都不是`Iterable<Number>`的子类型。因此，应改为

```java
// Wildcard type for parameter that serves as an E producer
public void pushAll(Iterable<? extends E> src) {
    for (E e : src)
        push(e);
}
```

要实现`popAll(Collection<E> dst)`方法，将 Stack 中的元素依次取出 add 到 dst 中，如果不用通配符实现：

```java
// popAll method without wildcard type - deficient!
public void popAll(Collection<E> dst) {
    while (!isEmpty())
        dst.add(pop());
}
```

同样地，假设有一个实例化`Stack<Number>`的对象 stack，dst 为`Collection<Object>`；调用 popAll 方法是会发生 type mismatch 错误，因为`Collection<Object>`不是`Collection<Number>`的子类型。因而，应改为：

```java
// Wildcard type for parameter that serves as an E consumer
public void popAll(Collection<? super E> dst) {
    while (!isEmpty())
        dst.add(pop());
}
```

在上述例子中，在调用 pushAll 方法时生产了 E 实例（produces E instances），在调用 popAll 方法时 dst 消费了 E 实例（consumes E instances）。Naftalin 与 Wadler 将 PECS 称为**Get and Put Principle**。

java.util.Collections 的 copy 方法(JDK1.7)完美地诠释了 PECS：

```java
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    int srcSize = src.size();
    if (srcSize > dest.size())
        throw new IndexOutOfBoundsException("Source does not fit in dest");

    if (srcSize < COPY_THRESHOLD ||
        (src instanceof RandomAccess && dest instanceof RandomAccess)) {
        for (int i=0; i<srcSize; i++)
            dest.set(i, src.get(i));
    } else {
        ListIterator<? super T> di=dest.listIterator();
        ListIterator<? extends T> si=src.listIterator();
        for (int i=0; i<srcSize; i++) {
            di.next();
            di.set(si.next());
        }
    }
}
```

PECS 总结：

- 要从泛型类取数据时，用 extends；
- 要往泛型类写数据时，用 super；
- 既要取又要写，就不用通配符（即 extends 与 super 都不用）。

### 泛型的约束

因为种种原因，Java 不能实现真正的泛型，只能使用类型擦除来实现伪泛型，这样虽然不会有类型膨胀问题，但是也引起来许多新问题，所以，SUN 对这些问题做出了种种限制，避免我们发生各种错误。

- [泛型类型的类型参数不能是值类型](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#instantiate)

```java
Pair<int, char> p = new Pair<>(8, 'a');  // 编译错误
```

- [不能创建类型参数的实例](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createObjects)

```java
public static <E> void append(List<E> list) {
    E elem = new E();  // 编译错误
    list.add(elem);
}
```

- [不能声明类型为类型参数的静态成员](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createStatic)

```java
public class MobileDevice<T> {
    private static T os; // error

    // ...
}
```

- [类型参数不能使用类型转换或 `instanceof`](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#cannotCast)

```java
public static <E> void rtti(List<E> list) {
    if (list instanceof ArrayList<Integer>) {  // 编译错误
        // ...
    }
}
List<Integer> li = new ArrayList<>();
List<Number>  ln = (List<Number>) li;  // 编译错误
```

- [不能创建类型参数的数组](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays)

```java
List<Integer>[] arrayOfLists = new List<Integer>[2];  // 编译错误
```

- [不能创建、catch 或 throw 参数化类型对象](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#cannotCatch)

```java
// Extends Throwable indirectly
class MathException<T> extends Exception { /* ... */ }    // 编译错误

// Extends Throwable directly
class QueueFullException<T> extends Throwable { /* ... */ // 编译错误
public static <T extends Exception, J> void execute(List<J> jobs) {
    try {
        for (J job : jobs)
            // ...
    } catch (T e) {   // compile-time error
        // ...
    }
}
```

- [仅仅是泛型类相同，而类型参数不同的方法不能重载](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#cannotOverload)

```java
public class Example {
    public void print(Set<String> strSet) { }
    public void print(Set<Integer> intSet) { } // 编译错误
}
```

### 为什么 JAVA 不支持泛型类型的数组?

- [分析一下为什么 JAVA 不支持泛型类型的数组](https://www.cnblogs.com/scutwang/articles/3735219.html)
- [java 为什么不支持泛型数组？](https://www.zhihu.com/question/20928981)

在 java 中是**”不能创建一个确切的泛型类型的数组”**的

下面的这个例子是不可以的：

```java
List<String>[] ls = new ArrayList<String>[10];
```

而使用通配符创建泛型数组是可以的，如下面这个例子：

```java
List<?>[] ls = new ArrayList<?>[10];
```

这样也是可以的：

```java
List<String>[] ls = new ArrayList[10];
```

下面使用[Sun](http://docs.oracle.com/javase/tutorial/extra/generics/fineprint.html)[的一篇文档](http://docs.oracle.com/javase/tutorial/extra/generics/fineprint.html)的一个例子来说明这个问题：

```java
List<String>[] lsa = new List<String>[10]; // Not really allowed.
Object o = lsa;
Object[] oa = (Object[]) o;
List<Integer> li = new ArrayList<Integer>();
li.add(new Integer(3));
oa[1] = li; // Unsound, but passes run time store check
String s = lsa[1].get(0); // Run-time error: ClassCastException.
```

> 这种情况下，由于 JVM 泛型的擦除机制，在运行时 JVM 是不知道泛型信息的，所以可以给 oa[1]赋上一个 ArrayList 而不会出现异常，但是在取出数据的时候却要做一次类型转换，所以就会出现 ClassCastException，如果可以进行泛型数组的声明，上面说的这种情况在编译期将不会出现任何的警告和错误，只有在运行时才会出错。
>
> 而对泛型数组的声明进行限制，对于这样的情况，可以在编译期提示代码有类型安全问题，比没有任何提示要强很多。

下面采用通配符的方式是被允许的:**数组的类型不可以是类型变量，除非是采用通配符的方式**，因为对于通配符的方式，最后取出数据是要做显式的类型转换的。

```java
List<?>[] lsa = new List<?>[10]; // OK, array of unbounded wildcard type.
Object o = lsa;
Object[] oa = (Object[]) o;
List<Integer> li = new ArrayList<Integer>();
li.add(new Integer(3));
oa[1] = li; // Correct.
Integer i = (Integer) lsa[1].get(0); // OK
```

### 编写一段泛型程序来实现 LinkedStack?

```java
public class LinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;

        Node() {
            item = null;
            next = null;
        }

        public Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    private Node<T> top = new Node<>();

    public void push(T item) {
        top = new Node<>(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> lss = new LinkedStack<>();

        for (String s : "A B C D".split(" ")) {
            lss.push(s);
        }

        String s;
        while ((s = lss.pop()) != null) {
            System.out.println(s);
        }
    }
}
```

### 编写一段泛型程序来实现 LRU 缓存?

## 设计模式
