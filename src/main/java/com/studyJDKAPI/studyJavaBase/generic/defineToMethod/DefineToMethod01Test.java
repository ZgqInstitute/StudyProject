package com.studyJDKAPI.studyJavaBase.generic.defineToMethod;

import org.junit.Test;

public class DefineToMethod01Test {

    @Test
    public void method01Test(){
        // 创建DefineToMethod01对象时不指定泛型，那么在使用method01方法时可以传任意类型的参数
        DefineToMethod01 defineToMethod01 = new DefineToMethod01();
        defineToMethod01.method01("aaa");
        defineToMethod01.method01(1);

        // 创建DefineToMethod01对象时指定了泛型，那么在使用method01方法时只能传指定类型
        // 泛型要写在前面才有效果
        DefineToMethod01 defineToMethod02 = new DefineToMethod01<String>();
        defineToMethod02.method01("aaa");
        defineToMethod02.method01(1);
        // 创建DefineToMethod01对象时指定了泛型，那么在使用method01方法时只能传指定类型
        DefineToMethod01<String> defineToMethod03 = new DefineToMethod01<>();
        defineToMethod03.method01("aaa");
//        defineToMethod03.method01(1);  这样写编译不通过

    }

    @Test
    public void method02Test(){
        DefineToMethod01<String> method2 = DefineToMethod01.method2("祝彦伊");
        String value = method2.getValue();
        System.out.println(value);
    }

    @Test
    public void method03Test(){
        DefineToMethod01<String> method3 = DefineToMethod01.method3("祝彦伊");
        String value = method3.getValue();
        System.out.println(value);
    }
}
