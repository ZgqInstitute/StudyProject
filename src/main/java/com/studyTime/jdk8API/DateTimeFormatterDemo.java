package com.studyTime.jdk8API;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class DateTimeFormatterDemo {
    public static void main(String[] args) {
        testDateTimeFormatter();
    }

    private static void testDateTimeFormatter(){
        /**
         * LocalDateTime转字符串
         * 由DateTimeFormatter来指定输出格式
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(formatter);
        System.out.println("time = " + time);

        /**
         * String转LocalDateTime
         */
        String stringTime = "1992/10/27 10:11:12";
        DateTimeFormatter formatter01 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(stringTime, formatter01);
        System.out.println("parse = " + parse);

        /**
         * LocalDate转字符串
         */
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDate localDate = LocalDate.now();
        String time2 = localDate.format(formatter2);
        System.out.println("time = " + time2);

        /**
         * String转LocalDate
         */
        String stringTime3 = "1992/10/27";
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate parse3 = LocalDate.parse(stringTime3, formatter3);
        System.out.println("parse = " + parse3);

        /**
         * LocalDate转字符串
         */
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        String time4 = localTime.format(formatter4);
        System.out.println("time = " + time4);

        /**
         * String转LocalDate
         */
        String stringTime5 = "10:11:12";
        DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime parse5 = LocalTime.parse(stringTime5, formatter5);
        System.out.println("parse = " + parse5);




        //加E可以显示周几
        DateTimeFormatter formatter02 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 E");

    }
}
