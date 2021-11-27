package com.zhangxianbing.learn.java.base.generics;

public class Wildcards {
    static void rawArg(Holder holder, Object arg) {
        holder.set(arg); // Warning: Unchecked call to 'set(T)' as a member of raw type Holder
        Object obj = holder.get();
    }

    static void unboundedArg(Holder<?> holder, Object arg) {
//        holder.set(arg); // 此处直接编译器报错了，holder.set需要类型为 capture of ?， 但提供类型为 Object
        Object obj = holder.get();
    }

    static <T> T exact1(Holder<T> holder) {
        return holder.get();
    }

    static <T> T exact2(Holder<T> holder, T arg) {
        holder.set(arg);
        return holder.get();
    }

    static <T> T wildSubType(Holder<? extends T> holder, T arg) {
//        holder.set(arg); // 编译不通过，holder.set需要<? extends T>类型，但arg是T类型
        return holder.get();
    }

    static <T> void wildSuperType(Holder<? super T> holder, T arg) {
        holder.set(arg);
//        T t = holder.get(); // 编译不通过： holder.get()返回的是 capture of ? super T 类型，不是T类型
        Object o = holder.get(); // 但可以赋值为Object类型
    }

    public static void main(String[] args) {
        Holder raw = new Holder();
        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<>();
        Holder<? extends Long> bounded = new Holder<>();

        Long lng = 1L;

        rawArg(raw, lng);
        rawArg(qualified, lng);
        rawArg(unbounded, lng);
        rawArg(bounded, lng);

        unboundedArg(raw, lng);
        unboundedArg(qualified, lng);
        unboundedArg(unbounded, lng);
        unboundedArg(bounded, lng);

        Object r1 = exact1(raw); // Warning: 此处exact1期望输入Holder<Object>, 但输入为Holder
        Long r2 = exact1(qualified);
        Object r3 = exact1(unbounded);
        Long r4 = exact1(bounded);

        Long r5 = exact2(raw, lng); // Warning: 此处exact2期望输入Holder<Long>, 但输入为Holder
        Long r6 = exact2(qualified, lng);
//        Long r7 = exact2(unbounded, lng); // Error: 此处exact2
//        Long r8 = exact2(bounded, lng);

        Long r9 = wildSubType(raw, lng);// Warning: 此处wildSubType期望输入Holder<? extends Long>, 但输入为Holder
        Long r10 = wildSubType(qualified, lng);
        Object r11 = wildSubType(unbounded, lng);
        Long r12 = wildSubType(bounded, lng);

        wildSuperType(raw, lng);// Warning: 此处wildSuperType期望输入Holder<? super Long>, 但输入为Holder
        wildSuperType(qualified, lng);
//        wildSuperType(unbounded, lng);
//        wildSuperType(bounded, lng);
    }
}
