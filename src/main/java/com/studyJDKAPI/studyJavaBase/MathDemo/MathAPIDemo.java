package com.studyJDKAPI.studyJavaBase.MathDemo;

import org.junit.Test;

import java.util.Random;

public class MathAPIDemo {

    @Test
    public void RandomTest() {
        Random random = new Random();
        // 生成一个[0, 10)的随机数，不包含10
        int i1 = random.nextInt(10);
        // 生成一个[1, 11)的随机数，不包含11
        int i2 = random.nextInt(10) + 1;
        // 生成一个[5, 11)的随机数，不包含11
        int i3 = random.nextInt(6) + 5;
        // 生成一个[100, 201)的随机数，不包含201
        int i4 = random.nextInt(101) + 100;
    }
}
