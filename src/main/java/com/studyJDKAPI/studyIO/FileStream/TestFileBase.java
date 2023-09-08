package com.studyJDKAPI.studyIO.FileStream;

import org.junit.Test;

import java.io.File;

/**
 * File对象的构造函数和字段演示
 */
public class TestFileBase {

    /**
     * 演示File对象的3个构造函数
     * 说明：文件或目录不管存不存在，都可以封装成File对象
     */
    @Test
    public void test01(){
        /**
         * 构造1：File(String pathname)
         * pathname为文件或文件夹的名字
         */
        File file1 = new File("zgq.txt");
        File file2 = new File("D:\\zgq.txt");

        /**
         * 构造2：File(String parent, String child)
         * parent为文件目录
         * child为文件名
         */
        File file3 = new File("D:\\","zgq.txt");

        /**
         * 构造3：File(File parent, String child)，跟构造2一样，只是parent传的是File对象
         * parent为文件目录,
         * child为文件名
         */
        File file4 = new File("D:\\");
        File file5 = new File(file4,"zgq.txt");
    }

    /**
     * 演示File的分隔符字段
     */
    @Test
    public void test02(){
        /**
         * windows下是使用 \\ 来作为分割
         * 但是linux下是使用 / 来分割
         * Q：如何根据不同的操作系统使用不同的分割符？
         */
        File file1 = new File("D:\\zgq.txt");


        /**
         * 方法一：使用System.getProperty("file.separator")
         */
        File file2 = new File("D:" + System.getProperty("file.separator") + "zgq.txt");


        /**
         * 方法二：使用File.separator
         * 方法一太low
         */
        File file3 = new File("D:" + File.separator + "zgq.txt");
        System.out.println(file3);

    }


}
