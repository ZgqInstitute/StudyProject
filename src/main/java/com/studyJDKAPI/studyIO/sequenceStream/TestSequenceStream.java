package com.studyJDKAPI.studyIO.sequenceStream;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

public class TestSequenceStream {

    /**
     * 需求：将多个文件（zgq.txt、xiaorong.txt、yiyi.txt）合并到一个文件
     */
    @Test
    public void test02() throws IOException {
        // 创建三个子输入流
        FileInputStream fileInputStream1 = new FileInputStream("zgq.txt");
        FileInputStream fileInputStream2 = new FileInputStream("xiaorong.txt");
        FileInputStream fileInputStream3 = new FileInputStream("yiyi.txt");

        /**
         * 使用Vector效率低，但是ArrayList又没有枚举
         * Q：如何让ArrayList产生枚举？
         * A：使用集合工具类Collections的enumeration()方法，可以由ArrayList得到枚举
         * 这里为了简单就直接使用Vector，可以直接得到枚举
         */
        Vector<FileInputStream> vector = new Vector<>();
        vector.add(fileInputStream1);
        vector.add(fileInputStream2);
        vector.add(fileInputStream3);
        Enumeration<FileInputStream> elements = vector.elements();

        /**
         * 创建序列化流SequenceInputStream
         * fileInputStream1、fileInputStream2、fileInputStream3三个流合并成了一个流SequenceInputStream
         */
        SequenceInputStream sequenceInputStream = new SequenceInputStream(elements);

        // 创建输出流。合并好的文件输出到哪里
        FileOutputStream fileOutputStream = new FileOutputStream("tatal.txt");
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = sequenceInputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
        }

        fileOutputStream.close();
        sequenceInputStream.close();
    }
}
