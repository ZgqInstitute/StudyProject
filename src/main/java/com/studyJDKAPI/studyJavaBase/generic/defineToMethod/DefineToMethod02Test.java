package com.studyJDKAPI.studyJavaBase.generic.defineToMethod;

import org.junit.Test;

public class DefineToMethod02Test {

    @Test
    public void method02Test(){
        DefineToMethod02 defineToMethod02 = new DefineToMethod02();
        defineToMethod02.method02("aaa");
        defineToMethod02.method02(1);

        String re = defineToMethod02.method03("刘秀");
        System.out.println(re);


    }
}
