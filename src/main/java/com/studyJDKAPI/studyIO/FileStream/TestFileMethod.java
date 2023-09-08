package com.studyJDKAPI.studyIO.FileStream;

import org.junit.Test;

import java.io.File;

/**
 * 演示File对象的方法
 */
public class TestFileMethod {

    /**
     * 演示File常用获取文件属性的方法
     */
    @Test
    public void test01(){
        File file = new File("zgq.txt");
        // 得到文件名
        System.out.println(file.getName());
        // 得到文件的绝对路径
        System.out.println(file.getAbsolutePath());
        // 得到文件的相对路径，new File("zgq.txt");构造写什么，得到的相对路径就是什么
        System.out.println(file.getPath());
        // 得到父目录
        System.out.println(file.getParent());
        // 得到文件大小
        System.out.println(file.length());
        // 得到文件的最后修改时间
        System.out.println(file.lastModified());
    }

    /**
     *
     */
    @Test
    public void test02(){

    }

    /**
     *
     */
    @Test
    public void test03(){

    }



}
