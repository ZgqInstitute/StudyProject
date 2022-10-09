package com.studyTime.jdk8API;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * LocalDate只包含年月日；
 */
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

        /**
         * 修改LocalDate的时间分量方法一：直接set某个时间分量
         * 说明：使用withMonth方法修改后会创建新的LocalDate对象；
         *       localDate04的月份还是之前的，localDate05的月份才是设置的7月。
         */
        LocalDate localDate04 = LocalDate.now();
        //将月份改为7月
        LocalDate localDate05 = localDate04.withMonth(7);
        //将号改为7号
        LocalDate localDate6 = localDate04.withDayOfMonth(7);

        /**
         * 修改LocalDate的时间分量方法二：对原来的LocalDate进行增减
         */
        LocalDate localDate07 = LocalDate.now();
        //localDate08是在localDate07基础上增加一年
        LocalDate localDate08 = localDate07.plusYears(1);
        //localDate09是在localDate07基础上减一年
        LocalDate localDate09 = localDate07.plusYears(-1);

        /**
         * LocalDate时间比较
         */
        LocalDate localDate10 = LocalDate.now();
        LocalDate localDate11 = LocalDate.of(1992, 10, 27);
        boolean equals = localDate10.equals(localDate11);
        boolean before = localDate10.isBefore(localDate11);
        boolean after = localDate10.isAfter(localDate11);
        int i = localDate10.compareTo(localDate11);

        /**
         * 计算2个LocalDate相差多少天，有2种方法
         */
        LocalDate localDate12 = LocalDate.of(1992, 11, 27);
        LocalDate localDate13 = LocalDate.of(1992, 10, 27);
        //方法1   toEpochDay()方法是得到与1970-01-01相差多少天
        long l = localDate12.toEpochDay() - localDate13.toEpochDay();
        //方法2
        long day = localDate12.until(localDate13, ChronoUnit.DAYS);
        //计算2个LocalDate相差几年
        long year = localDate12.until(localDate13, ChronoUnit.YEARS);
        //计算2个LocalDate相差几个月
        long month = localDate12.until(localDate13, ChronoUnit.MONTHS);

        /**
         * 通过MonthDay来判断周期性的日期，如生日、情人节
         * MonthDay对象只有月和日（同理：还有YearMonth类只有年和月）
         */
        LocalDate localDate14 = LocalDate.of(1992, 10, 27);
        //创建MonthDay的2种方法
        //方法1
        MonthDay of = MonthDay.of(10, 27);
        //方法2：由LocalDate得到MonthDay，去掉LocalDate中的年  说明：由MonthDay得到LocalDate，是调用MonthDay的atYear(int year)方法
        MonthDay from = MonthDay.from(localDate14);


    }
}
