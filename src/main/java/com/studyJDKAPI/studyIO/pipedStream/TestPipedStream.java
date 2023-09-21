package com.studyJDKAPI.studyIO.pipedStream;


import org.junit.Test;


import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class TestPipedStream {


    @Test
    public void test01() throws Exception {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();

        pipedInputStream.connect(pipedOutputStream);

        new Thread(new OutputClass(pipedOutputStream)).start();
        new Thread(new InputClass(pipedInputStream)).start();
    }

}
