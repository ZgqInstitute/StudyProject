package com.studyDesignPattern.factoryDemo.abstractFactory;

public class MysqlCommand implements ICommand {
    @Override
    public void command() {
        System.out.println("mysql命令");
    }
}
