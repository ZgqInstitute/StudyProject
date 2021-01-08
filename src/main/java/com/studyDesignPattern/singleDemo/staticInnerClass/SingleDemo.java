package com.studyDesignPattern.singleDemo.staticInnerClass;

public class SingleDemo {
    private SingleDemo() { }

    /**
     * 静态内部类的特点：当外部类加载的时候，静态内部类不会随之被加载（起到懒加载的目的）
     */
    private static class StaticInnerSingle {
        private static final SingleDemo SINGLE_DEMO = new SingleDemo();
    }

    /**
     * 通过外部类的public方法来调用静态内部类
     */
    public static SingleDemo getInstance() {
        //当使用到静态内部类才开始加载，使用类加载保证初始化实例只有一个线程，保证线程安全
        return StaticInnerSingle.SINGLE_DEMO;
    }
}
