package com.studyJDKAPI.studyIO;


import org.junit.Test;

import java.io.*;

/**
 * 转换流：InputStreamReader、OutputStreamWriter
 *
 * InputStreamReader 和 OutputStreamWriter都是字符流（以Reader和Writer结尾的都是字符流）
 *
 * InputStreamReader将字节流 转为 字符流。InputStreamReader使用指定的编码将读取的字节《解码》成字符。说明：由字节转向字符，一定需要指定编码。
 * OutputStreamWriter将字符流 转为 字节流。OutputStreamWriter使用指定的编码将读取的字符《编码》成字节。说明：由字符转向字节，一定需要指定编码。
 */
public class TestTransformStream {

    /**
     * 使用InputStreamReader将字节流转为字符流
     */
    @Test
    public void test01() throws IOException {
        // 创建字节流
        FileInputStream fileInputStream = new FileInputStream("zgq.txt");
        // 创建转换流InputStreamReader
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        // 创建字符流缓冲区
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String s;
        // 有了字符流，就可以使用字符流的特有方法，如：readLine()
        while ((s = bufferedReader.readLine()) != null) {
            System.out.println(s);
        }
        bufferedReader.close();
    }


    /**
     * 使用OutputStreamWriter将字符流转为字节流
     */
    @Test
    public void test02() throws IOException{
        /**
         * 创建字节输出流。最终输出还是靠这个字节输出流，要使用这个字节输出流out，需要借助OutputStreamWriter将字符流转为字节流out
         * 这里只是为了学习标准输出流才用out，完全可以使用FileOutputStream
         * FileOutputStream fileOutputStream = new FileOutputStream("xiaorong.txt");
         */
        OutputStream out = System.out;

        // 创建转换流（将字符流转为字节流）
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
        // 创建字符流缓冲区
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        /**
         * 调用转换流OutputStreamWriter的write方法，将字符流转为字节流(OutputStream)输出
         * 因为最终输出还是要使用字节流OutputStream来输出，必须输出字节，那为什么这里可以直接使用字符串bufferedWriter.write(String)
         * 就是因为使用了OutputStreamWriter将字符流转为了字节流
         */
        bufferedWriter.write("宝宝伊伊");
        bufferedWriter.close();
    }




}
