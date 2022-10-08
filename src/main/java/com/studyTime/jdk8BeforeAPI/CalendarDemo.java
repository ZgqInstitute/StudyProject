package com.studyTime.jdk8BeforeAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 说明：Date和Calendar
 *    1-Date和Calendar在使用时都没有对时间的正确性进行校验，如日的32还是可以创建成功；
 *    2-Date和Calendar输出的格式很不友好；
 *    3-使用SimpleDateFormat来格式化时，SimpleDateFormat是线程不安全的；
 *
 */
public class CalendarDemo {
    public static void main(String[] args) {
        testCalendar();
    }

    /**
     * Calendar是抽象类，不能直接new来创建对象
     * 通常使用Calendar的子类GregorianCalendar
     */
    private static void testCalendar(){
        /**
         * Calendar的get方法
         */
        //得到当前时间
        Calendar instance = Calendar.getInstance();
        //得到年
        int year = instance.get(Calendar.YEAR);
        //得到月  若当前是7月，那得到的month=6
        int month = instance.get(Calendar.MONTH);
        //2种方法得到号
        int day01 = instance.get(Calendar.DATE);
        int day02 = instance.get(Calendar.DAY_OF_MONTH);
        //得到时  Calendar.HOUR(得到12小时制)   Calendar.HOUR_OF_DAY(得到24小时制)
        int hour01 = instance.get(Calendar.HOUR);
        int hour02 = instance.get(Calendar.HOUR_OF_DAY);
        //得到周几  说明：1表示星期天  2表示星期一 ...
        int week = instance.get(Calendar.DAY_OF_WEEK);

        /**
         * 增减时间方法add()、roll()
         * add和roll方法的区别：
         *   add方法若增加的天数超出了当前月，那么Calendar的月份也会增加；
         *   roll方法若增加的天数超出了当前月，那么Calendar的月份还是保持不变；
         */
        Calendar instance01 = Calendar.getInstance();
        //当前时间增加1天
        instance01.add(Calendar.DAY_OF_MONTH, 1);
        //当前时间减1天
        instance01.add(Calendar.DAY_OF_MONTH, -1);

        /**
         * 通过Calendar创建指定时间
         */
        Calendar instance02 = Calendar.getInstance();
        instance02.set(1992,10,27);
        System.out.println("instance02 = " + instance02);

        /**
         * 通过Calendar创建指定格式时间
         * 方法：先将Calendar转为Date
         */
        Calendar instance03 = Calendar.getInstance();
        Date date = instance03.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        System.out.println("instance03 = " + format);

        /**
         * 将Date转为Calendar
         */
        Calendar instance04 = Calendar.getInstance();
        Date date1 = new Date("1992/10/14");
        instance04.setTime(date1);

    }

}
