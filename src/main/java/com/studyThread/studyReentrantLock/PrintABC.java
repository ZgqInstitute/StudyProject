package com.studyThread.studyReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替输出自己的线程名，每次输出5次，共输出3次
 */
public class PrintABC {

    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    int flag = 1;

    public static void main(String[] args) {
        PrintABC printABC = new PrintABC();
        new Thread(() -> {
            for(int i = 0; i < 3; i++){
                printABC.printA();
            }
        },"线程A").start();

        new Thread(() -> {
            for(int i = 0; i < 3; i++){
                printABC.printB();
            }
        },"线程B").start();

    }

    public void printA(){
        lock.lock();
        try{
            while(flag != 1){
                c1.await();
            }
            for(int i = 0; i < 5; i++){
                System.out.println(Thread.currentThread().getName());
            }
            flag++;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try{
            while(flag != 2){
                c2.await();
            }
            for(int i = 0; i < 5; i++){
                System.out.println(Thread.currentThread().getName());
            }
            flag--;
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
