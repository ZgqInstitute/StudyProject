package com.studyJAVABase.generic.defineToMethod;

/**
 * 方法上的泛型由类上的泛型来控制
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class DefineToMethod01<T> {

    public void method01(T t) {
        System.out.println(t);
    }

}
