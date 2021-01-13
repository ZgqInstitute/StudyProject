package com.studyDesignPattern.adapterDemo.classAdapter;


/**
 * 完成转化功能的对象(类的适配器模式，转化后还能使用转化前的功能，不推荐使用)
 */
public class Adapter extends Adaptee implements Target {

    @Override
    public int output5v() {
        int i = output220v();
        System.out.println("将" + i + "v电压转为5v电压的操作");
        return 5;
    }
}
