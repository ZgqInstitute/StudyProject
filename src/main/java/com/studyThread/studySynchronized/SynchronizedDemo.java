package com.studyThread.studySynchronized;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedDemo {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
