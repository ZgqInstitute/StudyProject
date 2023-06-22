package com.studyJDKAPI.studyAtomicReference.testIAddAdd;

import org.junit.Test;

/**
 * 测试变量加volatile修饰不能保证原子性
 */
public class Version01 {

    @Test
    public void noUseAtomicReferenceNoUseLock() {
        Demo demo = new Demo();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    demo.add();
                }
            }).start();
        }

        // 一定要加这个，不然子线程还没有执行主线程就输出num，那么肯定是小于100000的，加了这个表示要等子线程都执行完主线程才往下执行
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        long endTime = System.currentTimeMillis();
        // 正确的结果num应该等于100000，使用多线程操作结果会小于100000
        // 耗时大概为35毫秒
        System.out.println("num = " + demo.get() + "，耗时 = " + (endTime - startTime));
    }

    public class Demo {
        /**
         * 变量num加了volatile修饰，不能保证原子性
         */
        public volatile int num = 0;

        public void add() {
            /**
             * num++编译成汇编后是三条指令：
             * （1）getfield 从主内存将num拷贝到线程的工作内存；
             * （2）iadd 在主内存执行加1操作
             * （3）putfield 将加1后的值再刷新回主内存
             */
            num++;
        }

        public int get() {
            return num;
        }
    }

}
