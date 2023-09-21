package com.studyJDKAPI.studyIO.pipedStream;

import java.io.PipedInputStream;

public class InputClass implements Runnable {

    private PipedInputStream in;

    InputClass(PipedInputStream pipedInputStream) {
        in = pipedInputStream;
    }

    @Override
    public void run() {
        try {
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            String s = new String(bytes, 0, len);
            System.out.println(s);
            in.close();
        } catch (Exception e) {

        }
    }
}
