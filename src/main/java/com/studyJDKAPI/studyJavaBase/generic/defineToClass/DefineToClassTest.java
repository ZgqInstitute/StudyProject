package com.studyJDKAPI.studyJavaBase.generic.defineToClass;

import org.junit.Test;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class DefineToClassTest {
    @Test
    public void test(){
        GenericDefinitionToClass<String> generic = new GenericDefinitionToClass<>();
//        generic.setAa(1);  编译就会报错
        generic.setAa("zhuguangquan");
        System.out.println(generic.getAa());
    }
}
