package com.studyJDKAPI.studyIO.FileStream;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 演示File对象的方法
 */
public class TestFileMethod {

    /**
     * 演示File常用获取文件属性的方法
     */
    @Test
    public void test01() {
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
     * File对象的创建 和 删除，操作文件
     */
    @Test
    public void test02() throws IOException {
        File file = new File("yiyi.txt");
        // createNewFile方法是创建文件，会抛异常
        boolean newFile = file.createNewFile();
        // delete方法是删除文件
        boolean delete = file.delete();
    }

    /**
     * File对象的创建 和 删除，操作文件夹
     */
    @Test
    public void test03() {
        File file = new File("D:\\aa1");
        // mkdir方法是创建文件夹，只能创建一级目录，不能创建多级目录，要创建多级目录使用mkdirs()方法
        boolean mkdir = file.mkdir();

        // delete方法是删除文件夹，若文件夹内还有文件或文件夹，则删除失败
        boolean delete = file.delete();
    }


    /**
     * 判断是否存在方法
     */
    @Test
    public void test04() {
        File file = new File("D:\\aa1");
        // 判断文件是否存在
        boolean exists = file.exists();
    }

    /**
     * 修改文件名方法
     */
    @Test
    public void test05() {
        File file1 = new File("D:\\aa1");
        File file2 = new File("D:\\aa2");
        // 将D盘下的aa1文件夹重命名为aa2
        boolean b = file1.renameTo(file2);


        File file3 = new File("C:\\aa3");
        File file4 = new File("D:\\aa4");
        // 将C盘下的aa3文件夹移到到D盘下，并且名字为aa4
        boolean b2 = file3.renameTo(file4);
    }


    /**
     * 获取当前目录下所有文件和文件夹的名称，包括隐藏的文件
     */
    @Test
    public void test06() {
        // 必须封装目录，不能封装文件。说明，若不是目录，是文件的话，调用file的list方法得到的数组是null
        File file = new File("D:\\");
        /**
         * list()方法：获取当前目录下所有文件和文件夹的名称，包括隐藏的文件
         * 说明：list()返回的只是文件的名称，而listFiles()得到的是所有文件对象，更加强大。
         */
        String[] list = file.list();
        for (String s : list) {
            System.out.println(s);
        }
    }


    /**
     * 需求：查询指定目录所有内容，包括子目录（深度遍历）
     */
    @Test
    public void test07() {
        File file = new File("D:\\work_XR");
        listAllFile(file);
    }

    public void listAllFile(File file) {
        System.out.println("目录：" + file.getAbsolutePath());
        File[] files = file.listFiles();
        for (File file1 : files) {
            // 判断当前目录是否是文件夹，若是文件夹，继续递归调用列举方法
            if (file1.isDirectory()) {
                listAllFile(file1);
            } else {
                System.out.println("文件：" + file1.getAbsolutePath());
            }
        }
    }

    /**
     * 需求：删除一个带内容的目录
     */
    @Test
    public void test08() {
        File file = new File("D:\\work_XR - 副本");
        deleteAllFile(file);
    }

    public void deleteAllFile(File file) {
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                deleteAllFile(file1);
            } else {
                file1.delete();
            }
        }
        // 若不执行这一行，则只是将指定目录下的所有文件删掉，而文件夹并不会删掉。执行这一行，会将所有的文件夹都删掉
        file.delete();
    }

}
