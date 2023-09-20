package com.studyJDKAPI.studyIO.randomAccessStream;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 */
public class testRandomAccessFile {

    /**
     * 同一个RandomAccessFile对象的读写测试还没有成功，可能存在编码问题，有时间再看
     */
    @Test
    public void test01() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile("random.txt","rw");
        /**
         * 写字符串不要调用write(String)，这个会忽略字符的低位字节
         * 要写字符串使用byte
         */
        randomAccessFile.write("a".getBytes());
        /**
         * 写数字不要使用write(int)，这个会忽略数字字节的高位，只写低位字节
         * 使用writeInt(int)方法不会忽略，会将数字的四个字节全部写入文件
         */
        randomAccessFile.writeInt(97);

        byte[] bytes = new byte[2];
        int read = randomAccessFile.read(bytes);
        String readData = new String(bytes);
        System.out.println(readData);

        randomAccessFile.close();
    }
}
