# Java 基础

## 基本概念

### JRE or JDK

- JRE：Java Runtime Environment，Java 运行环境的简称，为 Java 的运行提供了所需的环境。它是一个 JVM 程序，主要包括了 JVM 的标准实现和一些 Java 基本类库。
- JDK：Java Development Kit，Java 开发工具包，提供了 Java 的开发及运行环境。JDK 是 Java 开发的核心，集成了 JRE 以及一些其它的工具，比如编译 Java 源码的编译器 javac 等

## 数据类型

### 装箱与拆箱

基本类型都有对应的包装类型，基本类型与其对应的包装类型之间的赋值使用自动装箱与拆箱完成。

```java
Integer x = 2;     // 装箱 调用了 Integer.valueOf(2)
int y = x;         // 拆箱 调用了 X.intValue()
```

### 缓存池

基本类型对应的缓冲池如下：

- boolean values true and false
- all byte values
- short values between -128 and 127
- int values between -128 and 127
- char in the range \u0000 to \u007F

在使用这些基本类型对应的包装类型时，如果该数值范围在缓冲池范围内，就可以直接使用缓冲池中的对象。

在 jdk 1.8 所有的数值类缓冲池中，Integer 的缓冲池 IntegerCache 很特殊，这个缓冲池的下界是 - 128，上界默认是 127，但是这个上界是可调的，在启动 jvm 的时候，通过 -XX:AutoBoxCacheMax=<size> 来指定这个缓冲池的大小，该选项在 JVM 初始化的时候会设定一个名为 java.lang.IntegerCache.high 系统属性，然后 IntegerCache 初始化的时候就会读取该系统属性来决定上界。

new Integer(123) 与 Integer.valueOf(123) 的区别在于：

- new Integer(123) 每次都会新建一个对象；
- Integer.valueOf(123) 会使用缓存池中的对象，多次调用会取得同一个对象的引用。

## String

### String 为什么是不可变的?

`String` 真正不可变有下面几点原因：

1. 保存字符串的数组被 `final` 修饰且为私有的，并且`String` 类没有提供/暴露修改这个字符串的方法。
2. `String` 类被 `final` 修饰导致其不能被继承，进而避免了子类破坏 `String` 不可变。

相关阅读：[如何理解 String 类型值的不可变？ - 知乎提问](https://www.zhihu.com/question/20618891/answer/114125846)

### String 不可变好处

**1. 可以缓存 hash 值**

因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。不可变的特性可以使得 hash 值也不可变，因此只需要进行一次计算。

**2. String Pool 的需要**

如果一个 String 对象已经被创建过了，那么就会从 String Pool 中取得引用。只有 String 是不可变的，才可能使用 String Pool。

**3. 安全性**

String 经常作为参数，String 不可变性可以保证参数不可变。例如在作为网络连接参数的情况下如果 String 是可变的，那么在网络连接过程中，String 被改变，改变 String 的那一方以为现在连接的是其它主机，而实际情况却不一定是。

**4. 线程安全**

String 不可变性天生具备线程安全，可以在多个线程中安全地使用。

### String Pool

字符串常量池（String Pool）保存着所有字符串字面量（literal strings），这些字面量在编译时期就确定。不仅如此，还可以使用 String 的 intern() 方法在运行过程将字符串添加到 String Pool 中。

当一个字符串调用 intern() 方法时，如果 String Pool 中已经存在一个字符串和该字符串值相等（使用 equals() 方法进行确定），那么就会返回 String Pool 中字符串的引用；否则，就会在 String Pool 中添加一个新的字符串，并返回这个新字符串的引用。

下面示例中，s1 和 s2 采用 new String() 的方式新建了两个不同字符串，而 s3 和 s4 是通过 s1.intern() 和 s2.intern() 方法取得同一个字符串引用。intern() 首先把 "aaa" 放到 String Pool 中，然后返回这个字符串引用，因此 s3 和 s4 引用的是同一个字符串。

```java
String s1 = new String("aaa");
String s2 = new String("aaa");
System.out.println(s1 == s2);           // false
String s3 = s1.intern();
String s4 = s2.intern();
System.out.println(s3 == s4);           // true
```

如果是采用 "bbb" 这种字面量的形式创建字符串，会自动地将字符串放入 String Pool 中。

```java
String s5 = "bbb";
String s6 = "bbb";
System.out.println(s5 == s6);  // true
```

在 Java 7 之前，String Pool 被放在运行时常量池中，它属于永久代。而在 Java 7，String Pool 被移到堆中。这是因为永久代的空间有限，在大量使用字符串的场景下会导致 OutOfMemoryError 错误。

### new String("abc")

使用这种方式一共会创建两个字符串对象（前提是 String Pool 中还没有 "abc" 字符串对象）。

- "abc" 属于字符串字面量，因此编译时期会在 String Pool 中创建一个字符串对象，指向这个 "abc" 字符串字面量；
- 而使用 new 的方式会在堆中创建一个字符串对象。

### String StringBuffer 和 StringBuilder 的区别是什么?

`StringBuilder` 与 `StringBuffer` 都继承自 `AbstractStringBuilder` 类，在 `AbstractStringBuilder` 中也是使用字符数组保存字符串，不过没有使用 `final` 和 `private` 关键字修饰，最关键的是这个 `AbstractStringBuilder` 类还提供了很多修改字符串的方法比如 `append` 方法。

**1. 可变性**

- String 不可变
- StringBuffer 和 StringBuilder 可变

**2. 线程安全**

- String 不可变，因此是线程安全的
- StringBuilder 不是线程安全的
- StringBuffer 是线程安全的，内部使用 synchronized 进行同步

**对于三者使用的总结：**

1. 操作少量的数据: 适用 `String`
2. 单线程操作字符串缓冲区下操作大量数据: 适用 `StringBuilder`
3. 多线程操作字符串缓冲区下操作大量数据: 适用 `StringBuffer`

补充：在 Java 9 之后，String 、`StringBuilder` 与 `StringBuffer` 的实现改用 byte 数组存储字符串 `private final byte[] value`

## 运算

### 参数传递

Java 的参数是以值传递的形式传入方法中，而不是引用传递。

相关问题：Java 里可以实现 Object 的 swap 函数吗？

### float 与 double

Java 不能隐式执行向下转型，因为这会使得精度降低。

1.1 字面量属于 double 类型，不能直接将 1.1 直接赋值给 float 变量，因为这是向下转型。

```java
// float f = 1.1;
```

1.1f 字面量才是 float 类型。

```java
float f = 1.1f;
```

### 隐式类型转换

因为字面量 1 是 int 类型，它比 short 类型精度要高，因此不能隐式地将 int 类型向下转型为 short 类型。

```java
short s1 = 1;
// s1 = s1 + 1;
```

但是使用 += 或者 ++ 运算符会执行隐式类型转换。

```java
s1 += 1;
s1++;
```

上面的语句相当于将 s1 + 1 的计算结果进行了向下转型：

```java
s1 = (short) (s1 + 1);
```

### switch

从 Java 7 开始，可以在 switch 条件判断语句中使用 String 对象。

```
String s = "a";
switch (s) {
    case "a":
        System.out.println("aaa");
        break;
    case "b":
        System.out.println("bbb");
        break;
}
```

switch 不支持 long、float、double，是因为 switch 的设计初衷是对那些只有少数几个值的类型进行等值判断，如果值过于复杂，那么还是用 if 比较合适。

## 关键字

### final

**1. 数据**

声明数据为常量，可以是编译时常量，也可以是在运行时被初始化后不能被改变的常量。

- 对于基本类型，final 使数值不变；
- 对于引用类型，final 使引用不变，也就不能引用其它对象，但是被引用的对象本身是可以修改的。

**2. 方法**

声明方法不能被子类重写。

private 方法隐式地被指定为 final，如果在子类中定义的方法和基类中的一个 private 方法签名相同，此时子类的方法不是重写基类方法，而是在子类中定义了一个新的方法。

**3. 类**

声明类不允许被继承。

### static

**1. 静态变量**

- 静态变量：又称为类变量，也就是说这个变量属于类的，类所有的实例都共享静态变量，可以直接通过类名来访问它。静态变量在内存中只存在一份。
- 实例变量：每创建一个实例就会产生一个实例变量，它与该实例同生共死。

**2. 静态方法**

静态方法在类加载的时候就存在了，它不依赖于任何实例。所以静态方法必须有实现，也就是说它不能是抽象方法。

只能访问所属类的静态字段和静态方法，方法中不能有 this 和 super 关键字，因为这两个关键字与具体对象关联。

**3. 静态语句块**

静态语句块在类初始化时运行一次。

```java
public class A {
    static {
        System.out.println("123");
    }

    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new A();
    }
}
// 123
```

**4. 静态内部类**

非静态内部类依赖于外部类的实例，也就是说需要先创建外部类实例，才能用这个实例去创建非静态内部类。而静态内部类不需要。

静态内部类不能访问外部类的非静态的变量和方法。

**5. 静态导包**

在使用静态变量和方法时不用再指明 ClassName，从而简化代码，但可读性大大降低。

```java
import static com.xxx.ClassName.*
```

**6. 初始化顺序**

静态变量和静态语句块优先于实例变量和普通语句块，静态变量和静态语句块的初始化顺序取决于它们在代码中的顺序。最后才是构造函数的初始化。

存在继承的情况下，初始化顺序为：

- 父类（静态变量、静态语句块）
- 子类（静态变量、静态语句块）
- 父类（实例变量、普通语句块）
- 父类（构造函数）
- 子类（实例变量、普通语句块）
- 子类（构造函数）

## Object 通用方法

### 概览

```java
public native int hashCode()

public boolean equals(Object obj)

protected native Object clone() throws CloneNotSupportedException

public String toString()

public final native Class<?> getClass()

protected void finalize() throws Throwable {}

public final native void notify()

public final native void notifyAll()

public final native void wait(long timeout) throws InterruptedException

public final void wait(long timeout, int nanos) throws InterruptedException

public final void wait() throws InterruptedException
```

### equals()

**1. 等价关系**

两个对象具有等价关系，需要满足以下五个条件：

Ⅰ 自反性

```java
x.equals(x); // true
```

Ⅱ 对称性

```java
x.equals(y) == y.equals(x); // true
```

Ⅲ 传递性

```java
if (x.equals(y) && y.equals(z))
    x.equals(z); // true;
```

Ⅳ 一致性

多次调用 equals() 方法结果不变

```java
x.equals(y) == x.equals(y); // true
```

Ⅴ 与 null 的比较

对任何不是 null 的对象 x 调用 x.equals(null) 结果都为 false

```java
x.equals(null); // false;
```

**2. 等价与相等**

- 对于基本类型，== 判断两个值是否相等，基本类型没有 equals() 方法。
- 对于引用类型，== 判断两个变量是否引用同一个对象，而 equals() 判断引用的对象是否等价。

```java
Integer x = new Integer(1);
Integer y = new Integer(1);
System.out.println(x.equals(y)); // true
System.out.println(x == y);      // false
```

**3. 实现**

- 检查是否为同一个对象的引用，如果是直接返回 true；
- 检查是否是同一个类型，如果不是，直接返回 false；
- 将 Object 对象进行转型；
- 判断每个关键域是否相等。

```java
public class EqualExample {

    private int x;
    private int y;
    private int z;

    public EqualExample(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EqualExample that = (EqualExample) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }
}
```

### hashCode()

hashCode() 返回哈希值，而 equals() 是用来判断两个对象是否等价。等价的两个对象散列值一定相同，但是散列值相同的两个对象不一定等价，这是因为计算哈希值具有随机性，两个值不同的对象可能计算出相同的哈希值。

在覆盖 equals() 方法时应当总是覆盖 hashCode() 方法，保证等价的两个对象哈希值也相等。

HashSet 和 HashMap 等集合类使用了 hashCode() 方法来计算对象应该存储的位置，因此要将对象添加到这些集合类中，需要让对应的类实现 hashCode() 方法。

下面的代码中，新建了两个等价的对象，并将它们添加到 HashSet 中。我们希望将这两个对象当成一样的，只在集合中添加一个对象。但是 EqualExample 没有实现 hashCode() 方法，因此这两个对象的哈希值是不同的，最终导致集合添加了两个等价的对象。

```java
EqualExample e1 = new EqualExample(1, 1, 1);
EqualExample e2 = new EqualExample(1, 1, 1);
System.out.println(e1.equals(e2)); // true
HashSet<EqualExample> set = new HashSet<>();
set.add(e1);
set.add(e2);
System.out.println(set.size());   // 2
```

理想的哈希函数应当具有均匀性，即不相等的对象应当均匀分布到所有可能的哈希值上。这就要求了哈希函数要把所有域的值都考虑进来。可以将每个域都当成 R 进制的某一位，然后组成一个 R 进制的整数。

R 一般取 31，因为它是一个奇素数，如果是偶数的话，当出现乘法溢出，信息就会丢失，因为与 2 相乘相当于向左移一位，最左边的位丢失。并且一个数与 31 相乘可以转换成移位和减法：`31*x == (x<<5)-x`，编译器会自动进行这个优化。

```java
@Override
public int hashCode() {
    int result = 17;
    result = 31 * result + x;
    result = 31 * result + y;
    result = 31 * result + z;
    return result;
}
```

### clone()

**1. cloneable**

clone() 是 Object 的 protected 方法，它不是 public，一个类不显式去重写 clone()，其它类就不能直接去调用该类实例的 clone() 方法。

```java
public class CloneExample {
    private int a;
    private int b;
}
CloneExample e1 = new CloneExample();
// CloneExample e2 = e1.clone(); // 'clone()' has protected access in 'java.lang.Object'
```

重写 clone() 得到以下实现：

```java
public class CloneExample {
    private int a;
    private int b;

    @Override
    public CloneExample clone() throws CloneNotSupportedException {
        return (CloneExample)super.clone();
    }
}
CloneExample e1 = new CloneExample();
try {
    CloneExample e2 = e1.clone();
} catch (CloneNotSupportedException e) {
    e.printStackTrace();
}
java.lang.CloneNotSupportedException: CloneExample
```

以上抛出了 CloneNotSupportedException，这是因为 CloneExample 没有实现 Cloneable 接口。

应该注意的是，clone() 方法并不是 Cloneable 接口的方法，而是 Object 的一个 protected 方法。Cloneable 接口只是规定，如果一个类没有实现 Cloneable 接口又调用了 clone() 方法，就会抛出 CloneNotSupportedException。

```java
public class CloneExample implements Cloneable {
    private int a;
    private int b;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

**2. 浅拷贝**

拷贝对象和原始对象的引用类型引用同一个对象。

```java
public class ShallowCloneExample implements Cloneable {

    private int[] arr;

    public ShallowCloneExample() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }

    @Override
    protected ShallowCloneExample clone() throws CloneNotSupportedException {
        return (ShallowCloneExample) super.clone();
    }
}
ShallowCloneExample e1 = new ShallowCloneExample();
ShallowCloneExample e2 = null;
try {
    e2 = e1.clone();
} catch (CloneNotSupportedException e) {
    e.printStackTrace();
}
e1.set(2, 222);
System.out.println(e2.get(2)); // 222
```

**3. 深拷贝**

拷贝对象和原始对象的引用类型引用不同对象。

```java
public class DeepCloneExample implements Cloneable {

    private int[] arr;

    public DeepCloneExample() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }

    @Override
    protected DeepCloneExample clone() throws CloneNotSupportedException {
        DeepCloneExample result = (DeepCloneExample) super.clone();
        result.arr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result.arr[i] = arr[i];
        }
        return result;
    }
}
DeepCloneExample e1 = new DeepCloneExample();
DeepCloneExample e2 = null;
try {
    e2 = e1.clone();
} catch (CloneNotSupportedException e) {
    e.printStackTrace();
}
e1.set(2, 222);
System.out.println(e2.get(2)); // 2
```

**4. clone() 的替代方案**

使用 clone() 方法来拷贝一个对象即复杂又有风险，它会抛出异常，并且还需要类型转换。Effective Java 书上讲到，最好不要去使用 clone()，可以使用拷贝构造函数或者拷贝工厂来拷贝一个对象。

```java
public class CloneConstructorExample {

    private int[] arr;

    public CloneConstructorExample() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public CloneConstructorExample(CloneConstructorExample original) {
        arr = new int[original.arr.length];
        for (int i = 0; i < original.arr.length; i++) {
            arr[i] = original.arr[i];
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }
}
CloneConstructorExample e1 = new CloneConstructorExample();
CloneConstructorExample e2 = new CloneConstructorExample(e1);
e1.set(2, 222);
System.out.println(e2.get(2)); // 2
```

##

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

如果子类的方法重写了父类的方法，那么子类中该方法的访问级别不允许低于父类的访问级别。这是为了确保可以使用父类实例的地方都可以使用子类实例去代替，也就是确保满足里氏替换原则。

字段决不能是公有的，因为这么做的话就失去了对这个字段修改行为的控制，客户端可以对其随意修改。

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

### super

- 访问父类的构造函数：可以使用 super() 函数访问父类的构造函数，从而委托父类完成一些初始化的工作。应该注意到，子类一定会调用父类的构造函数来完成初始化工作，一般是调用父类的默认构造函数，如果子类需要调用父类其它构造函数，那么就可以使用 super() 函数。
- 访问父类的成员：如果子类重写了父类的某个方法，可以通过使用 super 关键字来引用父类的方法实现。

### 重载和重写的区别

> 重载就是同样的一个方法能够根据输入数据的不同，做出不同的处理
>
> 重写就是当子类继承自父类的相同方法，输入数据一样，但要做出有别于父类的响应时，你就要覆盖父类方法

**重载**

发生在同一个类中（或者父类和子类之间），方法名必须相同，参数类型不同、个数不同、顺序不同，方法返回值和访问修饰符可以不同。

应该注意的是，返回值不同，其它都相同不算是重载。

综上：重载就是同一个类中多个同名方法根据不同的传参来执行不同的逻辑处理。

**重写**

存在于继承体系中，指子类实现了一个与父类在方法声明上完全相同的一个方法。

使用 @Override 注解，可以让编译器帮忙检查是否满足重写的限制条件。

综上：**重写就是子类对父类方法的重新改造，外部样子不能改变，内部逻辑可以改变。**

| 区别点     | 重载方法 | 重写方法                                                                                                                                             |
| :--------- | :------- | :--------------------------------------------------------------------------------------------------------------------------------------------------- |
| 发生范围   | 同一个类 | 子类                                                                                                                                                 |
| 参数列表   | 必须修改 | 一定不能修改                                                                                                                                         |
| 返回类型   | 可修改   | 子类方法返回值类型<=父类方法返回值类型                                                                                                               |
| 异常       | 可修改   | 子类方法声明抛出的异常类<=父类方法声明抛出的异常类                                                                                                   |
| 访问修饰符 | 可修改   | 子类方法访问权限>=父类方法访问权限（如果父类方法访问修饰符为 `private/final/static` 则子类就不能重写该方法，但是被 static 修饰的方法能够被再次声明） |
| 发生阶段   | 编译期   | 运行期                                                                                                                                               |
| 构造函数   | 可重载   | 不可重写构造函数                                                                                                                                     |

### Java 调用一个对象的方法时，其方法的查找顺序？

在调用一个方法时，先从本类中查找看是否有对应的方法，如果没有再到父类中查看，看是否从父类继承来。否则就要对参数进行转型，转成父类之后看是否有对应的方法。总的来说，方法调用的优先级为：

- this.func(this)
- super.func(this)
- this.func(super)
- super.func(super)

## 反射

每个类都有一个 **Class** 对象，包含了与类有关的信息。当编译一个新类时，会产生一个同名的 .class 文件，该文件内容保存着 Class 对象。

类加载相当于 Class 对象的加载，类在第一次使用时才动态加载到 JVM 中。也可以使用 `Class.forName("com.mysql.jdbc.Driver")` 这种方式来控制类的加载，该方法会返回一个 Class 对象。

反射可以提供运行时的类信息，并且这个类可以在运行时才加载进来，甚至在编译时期该类的 .class 不存在也可以加载进来。

Class 和 java.lang.reflect 一起对反射提供了支持，java.lang.reflect 类库主要包含了以下三个类：

- **Field** ：可以使用 get() 和 set() 方法读取和修改 Field 对象关联的字段；
- **Method** ：可以使用 invoke() 方法调用与 Method 对象关联的方法；
- **Constructor** ：可以用 Constructor 的 newInstance() 创建新的对象。

**反射的优点：**

- **可扩展性** ：应用程序可以利用全限定名创建可扩展对象的实例，来使用来自外部的用户自定义类。
- **类浏览器和可视化开发环境** ：一个类浏览器需要可以枚举类的成员。可视化开发环境（如 IDE）可以从利用反射中可用的类型信息中受益，以帮助程序员编写正确的代码。
- **调试器和测试工具** ： 调试器需要能够检查一个类里的私有成员。测试工具可以利用反射来自动地调用类里定义的可被发现的 API 定义，以确保一组测试中有较高的代码覆盖率。

**反射的缺点：**

尽管反射非常强大，但也不能滥用。如果一个功能可以不用反射完成，那么最好就不用。在我们使用反射技术时，下面几条内容应该牢记于心。

- **性能开销** ：反射涉及了动态类型的解析，所以 JVM 无法对这些代码进行优化。因此，反射操作的效率要比那些非反射操作低得多。我们应该避免在经常被执行的代码或对性能要求很高的程序中使用反射。
- **安全限制** ：使用反射技术要求程序必须在一个没有安全限制的环境中运行。如果一个程序必须在有安全限制的环境中运行，如 Applet，那么这就是个问题了。
- **内部暴露** ：由于反射允许代码执行一些在正常情况下不被允许的操作（比如访问私有的属性和方法），所以使用反射可能会导致意料之外的副作用，这可能导致代码功能失调并破坏可移植性。反射代码破坏了抽象性，因此当平台发生改变的时候，代码的行为就有可能也随着变化。
- [Trail: The Reflection API](https://docs.oracle.com/javase/tutorial/reflect/index.html)
- [深入解析 Java 反射（1）- 基础](http://www.sczyh30.com/posts/Java/java-reflection-1/)

## 异常

Throwable 可以用来表示任何可以作为异常抛出的类，分为两种： **Error** 和 **Exception**。其中 Error 用来表示 JVM 无法处理的错误，Exception 分为两种：

- **受检异常** ：需要用 try...catch... 语句捕获并进行处理，并且可以从异常中恢复；
- **非受检异常** ：是程序运行时错误，例如除 0 会引发 Arithmetic Exception，此时程序崩溃并且无法恢复。

![img](https://gitee.com/zhangxianbing/assets/raw/master/typora/2021-11-28-10-55-03_68747470733a2f2f63732d6e6f7465732d313235363130393739362e636f732e61702d6775616e677a686f752e6d7971636c6f75642e636f6d2f50506a77502e706e67)

- [Java Exception Interview Questions and Answers](https://www.journaldev.com/2167/java-exception-interview-questions-and-answersl)
- [Java 提高篇——Java 异常处理](https://www.cnblogs.com/Qian123/p/5715402.html)

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

## 注解

Java 注解是附加在代码中的一些元信息，用于一些工具在编译、运行时进行解析和使用，起到说明、配置的功能。注解不会也不能影响代码的实际逻辑，仅仅起到辅助性的作用。

[注解 Annotation 实现原理与自定义注解例子](https://www.cnblogs.com/acm-bingzi/p/javaAnnotation.html)

## 新特性

### Java 各版本的新特性

**New highlights in Java SE 11**

TODO

**New highlights in Java SE 8**

1. Lambda Expressions
2. Pipelines and Streams
3. Date and Time API
4. Default Methods
5. Type Annotations
6. Nashhorn JavaScript Engine
7. Concurrent Accumulators
8. Parallel operations
9. PermGen Error Removed

**New highlights in Java SE 7**

1. Strings in Switch Statement
2. Type Inference for Generic Instance Creation
3. Multiple Exception Handling
4. Support for Dynamic Languages
5. Try with Resources
6. Java nio Package
7. Binary Literals, Underscore in literals
8. Diamond Syntax

- [Difference between Java 1.8 and Java 1.7?](http://www.selfgrowth.com/articles/difference-between-java-18-and-java-17)
- [Java 8 特性](http://www.importnew.com/19345.html)

## 数据结构

### [HashMap 容量为什么总是为 2 的次幂？](https://www.cnblogs.com/javastack/p/13356402.html)

HashMap 是根据 key 的 hash 值决策 key 放入到哪个桶（bucket）中，通过 tab[(n - 1) & hash] 公式计算得出，其中 tab 是一个哈希表。

**为什么要通过 (n - 1) & hash 决定桶的索引呢？**

- &运算速度快，至少比%取模运算块

- (n - 1) & hash，当 n 为 2 次幂时，会满足一个公式：(n - 1) & hash = hash % n

![image-20211126151441769](https://gitee.com/zhangxianbing/assets/raw/master/typora/2021-11-26-15-14-49_image-20211126151441769.png)

##

## 设计模式
