package com.studyJDKAPI.studyIO.baseStream;

import org.junit.Test;

import java.io.*;

/**
 * 测试字符流的缓冲区操作，缓冲区的作用就是提高读写的效率
 * <p>
 * 说明：TestReaderAndWriterIO读例子中，读操作之前先创建的字符数组其实就是缓冲区，只是为了方便操作JDK将这个数组封装成对象了
 * 字符流的缓冲区(BufferedWriter、BufferedReader) = 字符流(Writer、Reader) + 缓存区（字符数组）
 * <p>
 * 对应类：
 * BufferedWriter extends Writer
 * BufferedReader extends Reader
 */
public class TestReaderAndWriterBuffer {


    /**
     * 测试BufferedWriter
     */
    @Test
    public void test01() throws IOException {
        // 先创建字符流
        Writer writer = new FileWriter("zgq.txt");
        // 创建字符流缓冲区，关联字符流
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        // 调用缓冲区的write方法输出
        bufferedWriter.write("zhuguangquan01");
        // BufferedWriter提供换行的方法，会识别不同操作系统加不同换行符
        bufferedWriter.newLine();
        bufferedWriter.write("zhuguangquan02");
        // 关闭BufferedWriter，其实就是关闭Writer
        bufferedWriter.close();
    }


    /**
     * 测试BufferedReader
     * <p>
     * BufferedReader读单个字符的read()方法是从缓冲区中读
     */
    @Test
    public void test02() throws IOException {
        // 先创建字符流
        FileReader fileReader = new FileReader("zgq.txt");
        // 创建字符流缓冲区，关联字符流
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int x;
        while ((x = bufferedReader.read()) != -1) {
            System.out.print((char) x);
        }
        bufferedReader.close();
    }


    /**
     * 测试BufferedReader
     * <p>
     * readLine()方法是读文件的一行
     */
    @Test
    public void test03() throws IOException {
        // 先创建字符流
        FileReader fileReader = new FileReader("zgq.txt");
        // 创建字符流缓冲区，关联字符流
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String s;

        while ((s = bufferedReader.readLine()) != null) {
            System.out.println(s);
        }

        bufferedReader.close();
    }

}
