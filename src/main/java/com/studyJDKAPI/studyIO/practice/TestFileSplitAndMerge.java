package com.studyJDKAPI.studyIO.practice;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 测试文件的分割和合并
 */
public class TestFileSplitAndMerge {


    /**
     * 说明：一个文件对应一个流！
     * <p>
     * 需求：
     * 本地有个大文件(关联一个输入流)，需要将大文件按照指定大小(这里指定每个文件大小等于1M)拆分成多个小文件(关联多个输出流)
     * 说明：拆分成多个小文件后，小文件是打不开的。
     */
    @Test
    public void test01() throws IOException {
        // 创建输入流，读取源文件
        FileInputStream fileInputStream = new FileInputStream("xiaorong.txt");
        // 定义1M的缓冲区
        byte[] buffer = new byte[1024 * 1024];

        // 创建输出流
        FileOutputStream fileOutputStream = null;
        int len = 0;
        int count = 1;

        // read()方法一定要加缓冲区，不然会一个字节创建一个文件
        while ((len = fileInputStream.read(buffer)) != -1) {
            /**
             * 说明：
             *    因为不确定读取的文件是什么类型，可以使用任意后缀，这里使用.part
             *    若拆分后的文件不想放在当前目录，那么可以使用FileOutputStream(File file)构造函数，其中File的构造使用：File(File parent, String child)，parent是目录名字，child是文件名字
             *
             *    这里就简单的将拆分后的文件输出到当前项目目录下
             */
            fileOutputStream = new FileOutputStream((count++) + ".part");
            fileOutputStream.write(buffer, 0, len);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }


    /**
     * 合并文件
     * 需求：将当前项目下的1.part、2.part、3.part、4.part、5.part文件合并成一个文件，
     * 合并后的文件也放在当前目录下
     */
    @Test
    public void test02() throws IOException {
        List<FileInputStream> list = new ArrayList<>();
        list.add(new FileInputStream("1.part"));
        list.add(new FileInputStream("2.part"));
        list.add(new FileInputStream("3.part"));
        list.add(new FileInputStream("4.part"));
        list.add(new FileInputStream("5.part"));
        // 调用集合工具类的方法得到枚举
        Enumeration<FileInputStream> enumeration = Collections.enumeration(list);
        // 使用序列流将多个流合并成一个流
        SequenceInputStream sequenceInputStream = new SequenceInputStream(enumeration);

        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("merge.txt");

        byte[] bytes = new byte[1024 * 1024];
        int len = 0;

        while ((len = sequenceInputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
        }
        sequenceInputStream.close();
        fileOutputStream.close();
    }


}
