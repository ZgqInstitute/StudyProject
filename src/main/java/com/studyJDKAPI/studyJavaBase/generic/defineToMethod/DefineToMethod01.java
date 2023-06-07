package com.studyJDKAPI.studyJavaBase.generic.defineToMethod;

/**
 * 方法上的泛型由类上的泛型来控制
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class DefineToMethod01<T> {

    /**
     * 这个方法并不是泛型方法
     * @param t
     */
    public void method01(T t) {
        System.out.println(t);
    }

}
