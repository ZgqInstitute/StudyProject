package com.studyJDKAPI.studyAtomicReference;

import org.junit.Test;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

public class testLongAccumulator {

    @Test
    public void test01() {
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 10);

        longAccumulator.accumulate(2);
        longAccumulator.accumulate(5);
        System.out.println(longAccumulator.get());
    }
}
