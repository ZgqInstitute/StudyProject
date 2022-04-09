package com.studyJAVABase.generic.defineToMethod;

import org.junit.Test;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class DefineToMethodTest {

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
        DefineToMethod02 defineToMethod02 = new DefineToMethod02();
        defineToMethod02.method02("aaa");
        defineToMethod02.method02(1);
    }

}
