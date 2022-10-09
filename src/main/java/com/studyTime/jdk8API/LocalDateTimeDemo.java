package com.studyTime.jdk8API;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * LocalDateTime对象有2个属性LocalDate和LocalTime（LocalDateTime = LocalDate + LocalTime）
 */
public class LocalDateTimeDemo {
    public static void main(String[] args) {
        testLocalDateTimeDemo();
    }

    private static void testLocalDateTimeDemo(){
        /**
         * 创建LocalDateTime
         */
        //方法1
        LocalDateTime localDateTime01 = LocalDateTime.now();
        //方法2
        LocalDateTime localDateTime02 = LocalDateTime.of(1992, 10, 27, 10, 11, 12);
        //方法3
        LocalDate now = LocalDate.now();
        LocalTime now1 = LocalTime.now();
        LocalDateTime localDateTime03 = LocalDateTime.of(now, now1);

        /**
         * 获取LocalDateTime的时间分量
         */
        LocalDateTime localDateTime04 = LocalDateTime.now();
        localDateTime04.getYear();
        localDateTime04.getMonth();
        localDateTime04.getDayOfMonth();
        localDateTime04.getHour();
        localDateTime04.getMinute();
        localDateTime04.getSecond();

        /**
         * 获取LocalDateTime中的LocalDate
         */
        LocalDateTime localDateTime05 = LocalDateTime.now();
        LocalDate localDate = localDateTime05.toLocalDate();

        /**
         * 获取LocalDateTime中的LocalTime
         */
        LocalDateTime localDateTime06 = LocalDateTime.now();
        LocalTime localTime = localDateTime06.toLocalTime();

        /**
         * 修改LocalDateTime
         */
        LocalDateTime localDateTime07 = LocalDateTime.now();
        localDateTime07.withYear(1992);
        localDateTime07.withMonth(10);
        localDateTime07.withDayOfMonth(27);

        /**
         * 比较LocalDateTime
         */
        LocalDateTime localDateTime08 = LocalDateTime.of(1992, 10, 27, 10, 11, 12);
        LocalDateTime localDateTime09 = LocalDateTime.of(1992, 10, 27, 10, 11, 12);
        boolean before = localDateTime08.isBefore(localDateTime09);
        boolean after = localDateTime08.isAfter(localDateTime09);

        /**
         * 计算2个LocalDateTime时间差
         */
        LocalDateTime localDateTime10 = LocalDateTime.of(1992, 10, 27, 10, 11, 12);
        LocalDateTime localDateTime11 = LocalDateTime.of(1993, 10, 27, 10, 11, 12);
        long until = localDateTime10.until(localDateTime11, ChronoUnit.YEARS);



    }
}
