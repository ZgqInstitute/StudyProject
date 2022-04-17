package com.studyThread.studyMutiThread.createThreadByRunnable;

public class Person implements Runnable {

    private String name;

    Person(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "===" + Thread.currentThread().getName() + "===" + i);
        }
    }

}