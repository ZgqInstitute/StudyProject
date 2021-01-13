package com.studyDesignPattern.adapterDemo.objectAdapter;

/**
 * 完成转化功能的对象(对象的适配器模式，转化后不能使用转化前的功能，推荐使用))
 */
public class Adapter implements Target {

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }
    @Override
    public int output5v() {
        int i = adaptee.output220v();

        System.out.println("将" + i + "v电压转为5v电压的操作");

        return 5;
    }
}
