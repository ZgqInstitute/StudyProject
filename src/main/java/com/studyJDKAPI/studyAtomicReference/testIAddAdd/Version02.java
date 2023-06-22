package com.studyJDKAPI.studyAtomicReference.testIAddAdd;

import org.junit.Test;

/**
 * 使用synchronized来保证i++的安全
 */
public class Version02 {

    @Test
    public void useSynchronized() {
        Demo02 demo = new Demo02();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    demo.add02();
                }
            }).start();
        }

        // 一定要加这个，不然子线程还没有执行主线程就输出num，那么肯定是小于10 0000的，加了这个表示要等子线程都执行完主线程才往下执行
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long endTime = System.currentTimeMillis();
        // 正确的结果num应该等于10 0000，加了synchronized修饰后num刚好等于10 0000
        // 耗时大概为50毫秒
        System.out.println("num = " + demo.get() + "，耗时 = " + (endTime - startTime));
    }


    public class Demo02 {
        public volatile int num = 0;

        public synchronized void add02() {
            /**
             * num++编译成汇编后是三条指令：
             * （1）getfield 从主内存将num拷贝到线程的工作内存；
             * （2）iadd 在主内存执行加1操作
             * （3）putfield 将加1后的值再刷新回主内存
             * 加了synchronized修饰后，上面的三步就能保证原子性！！！
             */
            num++;
        }

        public int get() {
            return num;
        }
    }

}
