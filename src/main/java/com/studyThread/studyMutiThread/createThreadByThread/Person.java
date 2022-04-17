package com.studyThread.studyMutiThread.createThreadByThread;

public class Person extends Thread {

    private String name;

    Person(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "===" + Thread.currentThread().getName() + "===" + i);
        }
    }

}
