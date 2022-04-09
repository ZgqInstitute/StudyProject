package com.studyJAVABase.generic.defineToClass;

/**
 * 泛型定义在类上
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class GenericDefinitionToClass<T> {

    private T aa;

    /**
     * 这个方法虽然使用了泛型，但是该方法并不是泛型方法
     */
    public T getAa() {
        return aa;
    }

    /**
     * 这个方法虽然使用了泛型，但是该方法并不是泛型方法
     */
    public void setAa(T aa) {
        this.aa = aa;
    }

}
