package com.studyJDKAPI.studyJavaBase.generic.defineToMethod;

import lombok.Data;

/**
 * 方法上的泛型由类上的泛型来控制
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
@Data
public class DefineToMethod01<T> {

    private T value;

    public DefineToMethod01(){

    }

    public DefineToMethod01(T value){
        this.value = value;
    }

    /**
     * 这个方法并不是泛型方法
     * @param t
     */
    public void method01(T t) {
        System.out.println(t);
    }

    public static <T> DefineToMethod01<T> method2(T t){
        return new DefineToMethod01<>(t);
    }

    /**
     * static与<T>之间也可以没有空格
     */
    public static<T> DefineToMethod01<T> method3(T t){
        return new DefineToMethod01<>(t);
    }



}
