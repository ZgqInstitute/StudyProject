package com.studyTime.jdk8API;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class LocalDateDemo {
    public static void main(String[] args) {
        testLocalDate();
    }

    /**
     * LocalDate只包含年月日，不包含时分秒，也不包含时区
     */
    private static void testLocalDate(){
        /**
         * LocalDate.now()得到当前年月日
         */
        LocalDate localDate01 = LocalDate.now();
        System.out.println("localDate01 = " + localDate01);

        /**
         * 通过of(int year, int month, int dayOfMonth)创建指定日期
         */
        LocalDate localDate02 = LocalDate.of(1992, 10, 27);

        /**
         * 根据LocalDate获取年、月、日、周几
         */
        LocalDate localDate03 = LocalDate.now();
        //======有2种获取年的方法=====
        localDate03.getYear();
        localDate03.get(ChronoField.YEAR);
        //======获取月份有3种方法=====
        //方法1
        localDate03.getMonth();//得到月份的枚举
        localDate03.getMonth().getValue();//得到月份的数值
        //方法2
        localDate03.getMonthValue();
        //方法3
        localDate03.get(ChronoField.MONTH_OF_YEAR);
        //======获取某天有2种方法=====
        localDate03.getDayOfMonth();
        localDate03.get(ChronoField.DAY_OF_MONTH);
        //======获取周几有3种方法=====
        //方法1
        localDate03.getDayOfWeek();//得到星期的枚举
        localDate03.getDayOfWeek().getValue();//得到周几的数值
        //方法2
        localDate03.get(ChronoField.DAY_OF_WEEK);

    }
}
