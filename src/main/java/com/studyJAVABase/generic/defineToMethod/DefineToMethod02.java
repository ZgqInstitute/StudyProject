package com.studyJAVABase.generic.defineToMethod;

/**
 * 方法参数上的泛型由方法前面的泛型来控制
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class DefineToMethod02 {

    public <T> void method02(T t){
        System.out.println(t);
    }

}
