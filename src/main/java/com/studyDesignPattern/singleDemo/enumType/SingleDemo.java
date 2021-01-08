package com.studyDesignPattern.singleDemo.enumType;


/**
 *使用枚举得到单例可以避免反射创建多实例
 */
public enum SingleDemo {

    SINGLE_DEMO;

    public void method(){
        System.out.println("hello");
    }
}
