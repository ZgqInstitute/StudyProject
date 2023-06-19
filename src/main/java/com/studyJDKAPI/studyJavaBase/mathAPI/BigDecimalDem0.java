package com.studyJDKAPI.studyJavaBase.mathAPI;

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
         * 应该避免使用double构造BigDecimal，因为：有些数字用double根本无法精确表示，传给BigDecimal构造方法时就已经不精确了。
         * 案例： new BigDecimal(0.1)得到的值是0.1000000000000000055511151231257827021181583404541015625。
         * *     使用new BigDecimal("0.1")得到的值是0.1。因此，如果需要精确计算，用String构造BigDecimal，避免用double构造，尽管它看起来更简单！
         */
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));

        /**
         * 比较BigDecimal数值大小使用compareTo()，不要使用equals()
         */
        BigDecimal b3 = new BigDecimal("0.1");
        BigDecimal b4 = new BigDecimal("0.10");
        System.out.println(b3.equals(b4));//false
        System.out.println(b3.compareTo(b4));//0



    }


}
