package com.studyJDKAPI.studyMathAPI;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalDem0 {

    @Test
    public void double_Test(){
        double a1 = 0.05;
        double a2 = 0.01;
        System.out.println(a1+a2);//0.060000000000000005精度损失

        double a3 = 1.0;
        double a4 = 0.42;
        System.out.println(a3-a4);//0.5800000000000001精度损失

        double a5 = 40.0123;
        double a6 = 100;
        System.out.println(a5*a6);//4001.2300000000005精度损失

        double a7 = 123.3;
        double a8 = 100;
        System.out.println(a7/a8);//1.2329999999999999精度损失
    }

    @Test
    public void bigDecimal_test(){
        /**
         * BigDecimal有下面2个构造函数，推荐使用BigDecimal(String val)
         * BigDecimal(String val)
         * BigDecimal(double val)
         */
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));


    }


}
