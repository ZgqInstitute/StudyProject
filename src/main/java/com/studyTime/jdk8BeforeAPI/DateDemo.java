package com.studyTime.jdk8BeforeAPI;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateDemo {
    public static void main(String[] args) {
        testDate();
    }

    private static void testDate(){

        /**
         * Sun Nov 27 00:00:00 CST 3892
         * 注：使用Date创建日期时，月份和年份比较特殊，需要注意！！！
         * Sun 表示星期天
         * Nov 表示11月，说明：使用Date时，使用0-11来表示12个月，若月份是10，那么应该写9
         * CST 表示北京时间
         * 3892 = 1992 + 1900（使用Date时，年份若使用整数表示会增加1900），所以要想表示1992，在year处应该写1992-1900=2
         */
        Date date01 = new Date(1992,10,27);
        System.out.println("date01 = " + date01);

        /**
         * 注：小时的表示范围是0-23，若要表示7点，hrs应该填6
         */
        Date date02 = new Date(1992,10,27,11,12);
        System.out.println("date02 = " + date02);

        /**
         * 使用Date(String s)构造
         * new Date("1992-10-27")字符串这样写是错误的
         * new Date("1992/10/27")字符串这样写是正确的(使用Date(String s)创建时年不用减1900)
         */
        Date date03 = new Date("1992/10/27");
        System.out.println("date03 = " + date03);

        /**
         * 使用Date的空参，创建当前时间
         */
        Date date04 = new Date();
        date04.getYear();//获取年
        date04.getMonth();//获取月
        date04.getDate();//获取日
        date04.getDay();//获取星期几   说明：星期天是0
        date04.getHours();//获取小时
        date04.getMinutes();//获取分
        date04.getSeconds();//获取秒

        /**
         * 格林时间（时间原点）：1970-01-01 00:00:00
         * 获取当前时间的毫秒值
         */
        Date date05 = new Date();
        System.out.println("date05 = " +date05.getTime());

        /**
         * 使用Date(long date)构造
         * 说明：若要创建比1970-01-01 00:00:00更早的时间，long传入负数就行
         */
        Date date06 = new Date(0);
        System.out.println("date06 = " + date06);

        /**
         * 若直接输出Date格式为Sun Nov 27 00:00:00 CST 3892
         * 使用toLocaleString()来将输出格式调整为我们易读格式：2022-10-8 15:15:40
         * 说明：使用toLocaleString()输出的格式是固定的
         */
        Date date07 = new Date();
        date07.toLocaleString();

        /**
         * Date的toLocaleString()方法只能输出固定格式的日期
         * 使用SimpleDateFormat来指定时间格式
         *
         * 小写的yyyy表示年；大写的YYYY表示星期所属的年份(若有一个星期跨了2年，那么取后一年)
         * 月份使用大写MM主要是为了和分的mm区分开来
         * 小写的dd表示本月的第几天；大写的DD表示这一天在本年中第几天
         * 时使用大写HH是24小时制，时使用小写hh是12小时制
         *
         */
        Date date08 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date08);
        System.out.println("date08 = " +format);
    }

}
