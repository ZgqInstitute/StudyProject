package com.studyDesignPattern.adapterDemo.objectAdapter;


import org.junit.Test;

public class TestClass {
    @Test
    public void testMethod() {
        Adaptee adaptee = new Adaptee();
        Target target = new Adapter(adaptee);
        target.output5v();

        //使用对象的适配器模式方式不可以输出220v，220v不是我们需要的，这种对象的适配器模式推荐使用
        //target.output220v();
    }
}
