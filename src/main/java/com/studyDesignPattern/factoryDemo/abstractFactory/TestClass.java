package com.studyDesignPattern.factoryDemo.abstractFactory;

import org.junit.Test;

public class TestClass {

    @Test
    public void testMethod(){
        AbstractFactory abstractFactory = new MysqlDataUtils();
        IConnection connection = abstractFactory.getConnection();
        connection.connect();

        ICommand command = abstractFactory.getCommand();
        command.command();
    }
}
