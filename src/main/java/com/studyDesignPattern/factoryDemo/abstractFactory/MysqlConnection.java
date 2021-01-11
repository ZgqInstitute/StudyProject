package com.studyDesignPattern.factoryDemo.abstractFactory;

public class MysqlConnection implements IConnection {
    @Override
    public void connect() {
        System.out.println("mysql连接");
    }
}
