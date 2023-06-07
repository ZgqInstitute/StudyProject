package com.studyJDKAPI.studyJavaBase.generic.defineToMethod;

/**
 * 方法参数上的泛型由方法前面的泛型来控制
 *
 *    <T> 这个叫：类型参数声明部分
 *    对于泛型方法，类型参数声明部分必须在方法返回类型之前
 *
 */
public class DefineToMethod02 {

    public <T> void method02(T t) {
        System.out.println(t);
    }

    /**
     * 类型参数<T>  可以被用来声明返回值类型
     */
    public <T> T method03(T t) {
        return t;
    }

    public <T extends Number> T method04(T t) {
        // todo 补充案例
        return t;
    }

}
