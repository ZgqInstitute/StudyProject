package com.studyJDKAPI.studyIO.objectStream;

import org.junit.Test;

import java.io.*;

public class TestObjectStream {

    /**
     * 使用ObjectOutputStream，将对象写到磁盘，叫持久化到磁盘，也叫序列化
     */
    @Test
    public void test01() throws IOException {
        // 将对象写到磁盘后，打开不了。存到磁盘的目的是下次要使用的时候直接从磁盘读，而不用再new，对象写到磁盘正规的后缀是.object
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("obj.object"));
        // 序列化（也叫持久化）Student对象，Student对象一定要实现Serializable接口
        objectOutputStream.writeObject(new Student("祝光泉", 20, 1027, "上海杨浦"));
        objectOutputStream.close();
    }

    /**
     * 使用ObjectInputStream的readObject()方法，读文件，实现反序列化得到对象。
     *
     * 说明：ObjectInputStream只能读ObjectOutputStream写的数据，别的流写的数据ObjectInputStream读不了
     */
    @Test
    public void test02() throws IOException, ClassNotFoundException {
        /**
         * 读obj.object文件可以使用字节流(FileInputStream)读到数据，但是不能将数据还原成对象
         * 要能读到数据还能还原成对象，要使用ObjectInputStream对象
         */
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("obj.object"));

        /**
         * 说明：这里读到的obj.object只是Student的对象，并不是Student.class，要创建对象堆中必须有字节码文件Student.class
         * 若obj.object是别人发给我的，那么我再执行objectInputStream.readObject()是得不到Student的，因为没有字节码文件
         */
        Student student = (Student)objectInputStream.readObject();

        System.out.println(student.getName());
        System.out.println(student.getAge());
        System.out.println(student.getNo());
        System.out.println(student.getAddress());
    }
}
