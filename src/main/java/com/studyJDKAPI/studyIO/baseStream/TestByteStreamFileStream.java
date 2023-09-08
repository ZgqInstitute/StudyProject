package com.studyJDKAPI.studyIO.baseStream;

import org.junit.Test;

import java.io.*;

/**
 * 测试字节流
 * <p>
 * 说明：对于字符，每一个字符都会在编码表有一个唯一对应的数字，但是媒体文件没有。
 * <p>
 * Q：为什么不能使用字符流读取媒体文件（音频、视频、图片等），将其复制到目的文件？
 * A：对于字符流，从硬盘读完数据后，不是立即往目的地写，而是先查编码表，若根据读到的字节在编码表查不到对于的字符，则会在编码表的未知字符区
 * 返回一个字符，这样会导致原文件与目的文件不一样
 */
public class TestByteStreamFileStream {

    /**
     * 使用FileOutputStream的write方法
     */
    @Test
    public void test01() throws IOException {
        // 创建字节输出流
        OutputStream outputStream = new FileOutputStream("zgqByte.txt");

        byte[] bytes = "光泉".getBytes();
        outputStream.write(bytes);

        // flush方法是父类OutputStream的方法，FileOutputStream并没有实现，所以调用FileOutputStream的flush方法无意义
        // outputStream.flush();
        outputStream.close();
    }


    /**
     * 使用FileOutputStream的read()方法，调用一次读一个字节
     */
    @Test
    public void test02() throws IOException {
        // 创建字节输入流
        InputStream inputStream = new FileInputStream("zgqByte.txt");
        // 一次读一个字节，若一个字符由多个字节组成，那么会读字符的前一个字节，会出现乱码
        int read = inputStream.read();
        System.out.println((char) read);
        inputStream.close();
    }

    /**
     * 使用FileOutputStream的read(byte[])方法
     */
    @Test
    public void test03() throws IOException {
        // 创建字节输入流
        InputStream inputStream = new FileInputStream("zgqByte.txt");
        byte[] bytes = new byte[3];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len));
        }
        inputStream.close();
    }

    /**
     * FileInputStream的available()方法是返回文件的字节数
     * available()方法慎用，若文件很大，可能导致内存溢出
     */
    @Test
    public void test04() throws IOException {
        // 创建字节输入流
        InputStream inputStream = new FileInputStream("zgqByte.txt");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        System.out.println(new String(bytes));
        inputStream.close();
    }


    /**
     * 需求：将D:\family\testIo目录下的bady.MP4视频复制到 D:\family目录下，文件名为badyYiYi.MP4
     * 实现一：读一个字节，写一个字节(注：这种方式不要使用，慢的一批！！！)
     */
    @Test
    public void test05_1() throws IOException {
        InputStream inputStream = new FileInputStream("D:\\family\\testIO\\baby.MP4");
        OutputStream outputStream = new FileOutputStream("D:\\family\\babyYiYi.MP4");

        int ch;
        while ((ch = inputStream.read()) != -1) {
            outputStream.write(ch);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * 需求：将D:\family\testIo目录下的bady.MP4视频复制到 D:\family目录下，文件名为badyYiYi.MP4
     * 实现二：直接使用字节流 + 自己创建缓冲区
     */
    @Test
    public void test05_2() throws IOException {
        InputStream inputStream = new FileInputStream("D:\\family\\testIO\\baby.MP4");
        OutputStream outputStream = new FileOutputStream("D:\\family\\babyYiYi.MP4");
        byte[] bufferBytes = new byte[2048];

        int len;
        while ((len = inputStream.read(bufferBytes)) != -1) {
            /**
             * inputStream.read(bufferBytes))：上面的这个方法是将数据读到bufferBytes数组
             * outputStream.write(bufferBytes)：这个方法是读取bufferBytes数组的数据到指定文件
             */
            outputStream.write(bufferBytes, 0, len);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * 需求：将D:\family\testIo目录下的bady.MP4视频复制到 D:\family目录下，文件名为badyYiYi.MP4
     * 实现三：使用字节流缓冲区
     */
    @Test
    public void test05_3() throws IOException {
        InputStream inputStream = new FileInputStream("D:\\family\\testIO\\baby.MP4");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        OutputStream outputStream = new FileOutputStream("D:\\family\\babyYiYi.MP4");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        int len;
        while ((len = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(len);
        }

        bufferedInputStream.close();
        bufferedOutputStream.close();
    }

    /**
     * 使用键盘输入流，只支持输入一个字符
     * 说明：使用 @Test 不能键盘录入，要使用main方法
     */
    @Test
    public void test06_1() throws IOException {
        // 创建键盘输入流
        InputStream in = System.in;
        // 阻塞方法。若没有读到数据一致阻塞，直到读到文件的结尾或发生异常才会终止
        int ch = in.read();
        System.out.println(ch);
        /**
         * 对于in（系统流）不需要关闭，因为系统流是static的，只有一个，一旦关闭之后，再通过System.in就获取不到系统流了，
         * 关闭后要再次获取系统流只能重新启动系统
         */
        // in.close();
    }

    /**
     * 使用键盘输入流，支持多次输入字符
     */
    @Test
    public void test06_2() throws IOException {
        // 创建键盘输入流
        InputStream in = System.in;
        int ch;
        while ((ch = in.read()) != -1) {
            System.out.println(ch);
        }
    }


}
