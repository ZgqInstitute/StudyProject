package com.studyJAVABase.generic.defineToStaticMethod;

/**
 * 静态方法使用泛型
 *
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class DefineToStaticMethod<T> {

    /**
     * 静态方法上的泛型不能由类上的泛型决定。不能这样使用，因为：
     *    静态方法会随类的class文件加载到内存跟着加载到内存，但此时还没有创建对象，所以类现在还不能使用，
     *    但是类中的静态方法是可以直接使用的。（静态方法是优先于对象存在的）
     *    若对象还没有创建就使用静态方法(静态方法上的泛型依赖于类上的泛型，由于类还没有创建那么泛型也没有确定)，编译不通过
     */
//    public static void method01(T t){
//        System.out.println(t);
//    }

    /**
     * 静态方法上的泛型不能由类上的泛型决定，但是可以定义泛型方法，由方法上的泛型来决定
     */
    public static <E> void method02(E e){
        System.out.println(e);
    }

}
