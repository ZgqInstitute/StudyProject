package com.studyJAVABase.generic.defineToSuperAndExtends;

/**
 * 泛型方法在使用上下边界限定注意事项
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class SuperAndExtends {

    /**
     * 泛型方法在使用上下限定时，不能放在方法的参数上
     */
//    public <E> void method01(Generic<E extends Number> generic){
//
//    }

    /**
     * 泛型方法在使用上下限定时，要放在返回值的前面
     */
    public <E extends Number> void method02(Generic<E> generic){

    }

}
