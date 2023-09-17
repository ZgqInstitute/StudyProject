package com.studyJDKAPI.studyIO.practice;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 说明：一个文件对应一个流！
 * <p>
 * 需求：
 * 本地有个大文件(关联一个输入流)，需要将大文件按照指定大小(这里指定每个文件大小等于1M)拆分成多个小文件(关联多个输出流)
 * 说明：拆分成多个小文件后，小文件是打不开的。
 */
public class TestFileSplit {


    @Test
    public void test01() throws IOException {
        // 创建输入流，读取源文件
        FileInputStream fileInputStream = new FileInputStream("xiaorong.txt");
        // 定义1M的缓冲区
        byte[] buffer = new byte[1024*1024];

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


}
