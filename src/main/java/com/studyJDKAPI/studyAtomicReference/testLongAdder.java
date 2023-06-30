package com.studyJDKAPI.studyAtomicReference;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

public class testLongAdder {

    /**
     * LongAdder简单Demo
     */
    @Test
    public void test01() {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.sum());
    }

    /**
     * 测试各种原子类型实现i++的耗时，LongAdder和AtomicLong完胜！！
     *
     * 需求：50个线程，每个线程加 100 0000 次，测试不同方法的耗时
     */
    @Test
    public void test02() {
        Demo4 demo4 = new Demo4();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 1; j <= 1000000; j++) {
//                        demo4.doSynchronized();// 耗时2300毫秒
//                        demo4.doAtomicLong();// 耗时1500毫秒
                        demo4.doLongAdder();// 耗时150毫秒
//                        demo4.doLongAccumulator();// 耗时150毫秒
                    }
                }
            }, "线程" + i).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("结果num：" + demo4.num + "  耗时：" + (endTime - startTime));
//        System.out.println("结果num：" + demo4.atomicLong.get() + "  耗时：" + (endTime - startTime));
        System.out.println("结果num：" + demo4.longAdder.sum() + "  耗时：" + (endTime - startTime));
//        System.out.println("结果num：" + demo4.longAccumulator.get() + "  耗时：" + (endTime - startTime));
    }


    public class Demo4 {
        int num = 0;

        /**
         * 实现方式1：采用synchronized
         */
        public synchronized void doSynchronized() {
            num++;
        }

        /**
         * 实现方式2：采用AtomicLong
         */
        AtomicLong atomicLong = new AtomicLong(0);
        public void doAtomicLong() {
            atomicLong.getAndIncrement();
        }

        /**
         * 实现方式3：采用LongAdder
         */
        LongAdder longAdder = new LongAdder();
        public void doLongAdder(){
            longAdder.increment();
        }


        /**
         * 实现方式4：采用LongAccumulator
         */
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 0);
        public void doLongAccumulator(){
            longAccumulator.accumulate(1);
        }

    }


}
