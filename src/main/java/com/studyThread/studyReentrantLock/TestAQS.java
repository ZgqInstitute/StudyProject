package com.studyThread.studyReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class TestAQS {

    public void test(){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        lock.unlock();
    }
}
