package com.studyDesignPattern.factoryDemo.abstractFactory;

public class MysqlDataUtils implements AbstractFactory {
    @Override
    public IConnection getConnection() {
        return new MysqlConnection();
    }

    @Override
    public ICommand getCommand() {
        return new MysqlCommand();
    }
}
