package com.studyJDKAPI.studyIO.printStream;

import org.junit.Test;

import java.io.PrintWriter;

/**
 * 测试打印字符输出流PrintWriter
 * 说明：tomcat在给客户端响应时，就是使用PrintWriter流给客户端输出
 */
public class TestPrintWriter {

    /**
     * PrintWriter
     */
    @Test
    public void test01() {
        /**
         * 创建字符打印流
         * 构造函数参数可以写：File对象、字符串、OutputStream、Writer
         */
        PrintWriter printWriter = new PrintWriter(System.out);// 输出目的使用System.out（OutputStream），输出到控制台

        printWriter.print("祝光泉");

        printWriter.close();
    }



}
