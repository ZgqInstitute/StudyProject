package com.studyThread.studySynchronized;

/**
 * 剖析synchronized的原理
 */
public class SynchronizedPrinciple {

    Object lock = new Object();

    public void syncTest(){
        /**
         * 1)synchronized锁的是 () 内的锁对象，而不是 {} 内的代码块；
         * 2)ReentrantLock加锁是将state的状态从0变为1，而synchronized加锁是改变 锁对象 的对象头；
         */
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + "    aa");
            System.out.println(Thread.currentThread().getName() + "    bb");
        }
    }

}
