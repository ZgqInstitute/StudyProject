package com.studyTime.jdk8API;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * 1. LocalTime只包含时分秒；
 * 2. LocalTime不包含时区消息；
 */
public class LocalTimeDemo {
    public static void main(String[] args) {
        testLocalTime();
    }

    private static void testLocalTime(){
        /**
         * 创建LocalTime对象的方法
         */
        //使用LocalTime创建当前时间
        LocalTime localTime01 = LocalTime.now();
        //使用LocalTime创建指定时间
        LocalTime localTime02 = LocalTime.of(10, 11, 12);
        //使用LocalTime创建一天的正午时间  12:00
        LocalTime localTime03 = LocalTime.NOON;
        //使用LocalTime创建一天的最小时间  00:00
        LocalTime localTime04 = LocalTime.MIN;
        //使用LocalTime创建一天的最大时间  23:59:59.999999999
        LocalTime localTime05 = LocalTime.MAX;
        //使用LocalTime创建一天的午夜时间  00:00
        LocalTime localTime06 = LocalTime.MIDNIGHT;

        /**
         * 获取LocalTime的时间分量
         */
        LocalTime localTime07 = LocalTime.now();
        localTime07.getHour();
        localTime07.getMinute();
        localTime07.getSecond();

        /**
         * 修改LocalTime
         */
        LocalTime localTime08 = LocalTime.now();
        LocalTime localTime09 = localTime08.withHour(1);
        LocalTime localTime10 = localTime08.withMinute(1);
        LocalTime localTime11 = localTime08.withSecond(1);

        /**
         * 比较LocalTime
         */
        LocalTime localTime12 = LocalTime.of(13, 11, 12);
        LocalTime localTime13 = LocalTime.of(10, 11, 12);
        boolean equals = localTime12.equals(localTime13);
        boolean before = localTime12.isBefore(localTime13);
        boolean after = localTime12.isAfter(localTime13);

        /**
         * 计算LocalTime的时间差
         */
        LocalTime localTime14 = LocalTime.of(13, 11, 12);
        LocalTime localTime15 = LocalTime.of(10, 11, 12);
        long until = localTime14.until(localTime15, ChronoUnit.HOURS);
        System.out.println(until);


    }
}
