package com.studyDesignPattern.factoryDemo.abstractFactory;

/**
 * 该接口的作用：聚合了一系列相关功能(跟数据库相关：如连接、命令等对象)，而不用指定他们具体的类
 *
 * IConnection接口抽取了数据库连接的公共方法：连接
 * ICommand接口抽取了操作数据库命令的公共方法：操作命令
 * 本接口将上面连个接口进行聚合
 */
public interface AbstractFactory {

    IConnection getConnection();

    ICommand getCommand();
}
