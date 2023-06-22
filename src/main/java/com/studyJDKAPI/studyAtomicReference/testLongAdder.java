package com.studyJDKAPI.studyAtomicReference;

import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

public class testLongAdder {

    @Test
    public void test01(){
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.sum());
    }

}
