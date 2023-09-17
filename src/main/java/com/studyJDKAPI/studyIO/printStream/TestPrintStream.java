package com.studyJDKAPI.studyIO.printStream;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

/**
 * 测试打印字节输出流PrintStream
 */
public class TestPrintStream {

    /**
     * PrintStream的write()方法和print()方法使用
     */
    @Test
    public void test01() throws IOException {
        // 创建打印流PrintStream
        PrintStream printStream = new PrintStream("print.txt");
        /**
         * 调用write(97)方法，输出到print.txt文件的是a。write(int b)方法只写int 4个字节中的低8位，高位不写
         * 调用print()方法，输出到print.txt文件的是97。因为print()方法是原样输出，会先将97转为字符串，再输出
         */
        printStream.write(97);
        printStream.close();
    }
}
