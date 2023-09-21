package com.studyJDKAPI.studyIO.pipedStream;

import java.io.PipedOutputStream;

public class OutputClass implements Runnable{

    private PipedOutputStream out;

    OutputClass(PipedOutputStream pipedOutputStream){
        out = pipedOutputStream;
    }


    @Override
    public void run() {
        try{
            out.write("祝光泉管道流".getBytes());
            out.close();
        }catch (Exception e){

        }
    }
}
