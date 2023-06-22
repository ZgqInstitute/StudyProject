package com.studyJDKAPI.studyAtomicReference.testIAddAdd;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用CAS思想
 * 使用原子整形来保证i++的安全
 */
public class Version03 {

    @Test
    public void useAtomicReference(){
        Demo03 demo = new Demo03();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    demo.add03();
                }
            }).start();
        }

        // 一定要加这个，不然子线程还没有执行主线程就输出num，那么肯定是小于10 0000的，加了这个表示要等子线程都执行完主线程才往下执行
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long endTime = System.currentTimeMillis();
        // 正确的结果num应该等于10 0000，加了synchronized修饰后num刚好等于10 0000
        // 耗时大概为38毫秒
        System.out.println("num = " + demo.atomicInteger + "，耗时 = " + (endTime - startTime));
    }



    public class Demo03 {
        /**
         * AtomicInteger有个被volatile修饰的属性
         */
        AtomicInteger atomicInteger = new AtomicInteger();

        public void add03(){
            atomicInteger.getAndIncrement();
        }

    }

}
