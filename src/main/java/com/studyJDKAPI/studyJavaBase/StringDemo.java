package com.studyJDKAPI.studyJavaBase;

import org.junit.Test;

public class StringDemo {
    @Test
    public void stringTest() {
        String s1 = new String("abc");
        String s2 = "abc";
        String s3 = s1.intern();//s3是字符串abc在堆中的地址，不是对象s1 String的地址
        String s4 = s2.intern();//s4是字符串abc在堆中的地址
        System.out.println(s1 == s2);//false  s1是String的地址  s2是字符串abc在堆中的地址
        System.out.println(s1 == s3);//false
        System.out.println(s1 == s4);//false
        System.out.println(s2 == s4);//true
        System.out.println(s3 == s4);//true
    }

    @Test
    public void stringAddTest(){
        String s1 = "nb" + "a";
        System.out.println(s1.intern() == s1);//true

        String s2 = "ja" + "va";
        System.out.println(s2 == s2.intern());//true
    }

    @Test
    public void stringBufferAddTest(){
        String s1 = new StringBuffer("nb").append("a").toString();
        System.out.println(s1.intern() == s1);

        String s2 = new StringBuffer("ja").append("va").toString();
        System.out.println(s2 == s2.intern());//false  因为jdk有个Version类有个字符串常量java
    }


}
