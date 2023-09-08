package com.studyJDKAPI.studyIO.baseStream;

import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 测试字符流的基本 读、写 操作
 */
public class TestReaderAndWriterIO {

    /**
     * 需求：将内存中的数据输出到外围设备
     *
     * 注：FileWriter也是转换流，因为FileWriter extends OutputStreamWriter
     *     FileWriter可以很方便的操作文本文件，但不能操作媒体文件。
     *     FileWriter = 操作文件的字节流 + 本机默认的编码表
     *     FileWriter不能指定编码表，若要指定编码表，就使用FileWriter的父类OutputStreamWriter
     */
    @Test
    public void test01() throws IOException {
        /**
         * new FileWriter()是创建一个可以往文件中写入字符数据的《字符流》输出对象
         *   1-若zgq.txt文件不存在，则会在项目同目录下创建zgq.txt文件；
         *   2-若zgq.txt文件已经存在，则会覆盖原文件
         *   3-FileWriter fileWriter = new FileWriter("zgq.txt");  调用fileWriter.write()方法是覆盖写。同一次运行会追加写，第二次运行会覆盖第一次的内容
         *   4-FileWriter fileWriter = new FileWriter("zgq.txt", true);  调用fileWriter.write()方法是追加（续写）写
         */
        FileWriter fileWriter = new FileWriter("zgq.txt");
        /**
         * Writer的write方法是将数据写到流里(临时缓冲区，就是还在内存，没什么高深的)，数据还没有写到目标文件zgq.txt
         *   1- \r\n是windows的换行
         *   2- \n是linux的换行
         */
        fileWriter.write("cp7\r\ncp8");
        fileWriter.write("cp9\r\ncp10");
        /**
         * flush方法是刷新流的缓冲区，若流已经多次调用write()方法，则一次写到预期目标
         * flush方法不用单独调用，因为close方法包含flush方法
         * flush方法可以调用多次，close只能调一次
         */
//        fileWriter.flush();
        /**
         * 关闭流。
         *   因为在将数据写到外围设备时，需要调用操作系统的资源来处理，你写到外围设备后需要将这个占有的资源释放掉
         *   close()方法内部调了flush方法
         *   若关闭流之后，再调用write或flush方法将抛IOException异常
         */
        fileWriter.close();
    }


    /**
     * 案例：IO异常处理方式
     */
    @Test
    public void test02() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("zgq.txt");
            fileWriter.write("cp3");
        } catch (IOException e) {
            // 处理异常
        } finally {
            if (fileWriter != null) {
                try {
                    // 需要在if内执行，因为若new FileWriter("zgq.txt");执行失败，那么fileWriter对象就为null，会报NPE
                    fileWriter.close();
                } catch (IOException e) {
                    // 处理异常
                }
            }
        }
    }


    /**
     * 需求：从外围设备读一个字符数据到内存
     */
    @Test
    public void test03_1() throws IOException {
        // 创建流对象
        FileReader fileReader = new FileReader("zgq.txt");

        /**
         * fileReader.read();是读目标文件的第一个字符，返回值是该字符对应的二进制。eg：若文件的第一个字符为a，那么返回97
         * 再调一次read()方法返回第二个字符
         * 若已经读到最后了，read()方法返回-1
         */
        int result = fileReader.read();
        char resultChar = (char) result;// result 对应的字符

        fileReader.close();
    }

    /**
     * 需求：从外围设备文件的所有数据到内存
     */
    @Test
    public void test03_2() throws IOException {
        FileReader fileReader = new FileReader("zgq.txt");
        int ch;
        while ((ch = fileReader.read()) != -1) {
            System.out.println((char) ch);
        }
        fileReader.close();
    }

    /**
     * 案例：使用 read(char[] ch) API，返回值是读到的字符数
     * <p>
     * 说明：若文件zgq.txt有数据：abcde
     * 创建大数据为：char[] ch = new char[3];
     * 读第一次得到的数据：abc
     * 读第二次得到的数据：dec  因为读第二次会将数组前两个字符替换掉
     * 读第三次得到的数据：dec  因为前二次已经读完了，读第三次没有得到数据，数组还是读第二次的
     */
    @Test
    public void test04_1() throws IOException {
        // 创建流对象
        FileReader fileReader = new FileReader("zgq.txt");
        // 先创建数组。数组的大小表示读文件前几个字符
        char[] ch = new char[3];
        // 将读到的字符存储到数组，返回值是读到的字符数
        int result = fileReader.read(ch);
        System.out.println(result);
        System.out.println(new String(ch));
        fileReader.close();
    }

    /**
     * test04_1的优化
     */
    @Test
    public void test04_2() throws IOException {
        FileReader fileReader = new FileReader("zgq.txt");
        char[] ch = new char[3];
        int len = 0;
        // read(char[] ch)方法返回值只要不是-1就还没有读完，返回值为-1说明已经读完
        while ((len = fileReader.read(ch)) != -1) {
            System.out.println(new String(ch, 0, len));
        }
        fileReader.close();
    }

}
