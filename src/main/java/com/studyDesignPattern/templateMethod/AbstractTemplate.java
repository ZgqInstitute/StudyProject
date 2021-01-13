package com.studyDesignPattern.templateMethod;

/**
 * 抽象的模板类
 * 模板方法的应用：（1）在javax.servlet.http.HttpServlet
 *                （2）org.springframework.web.servlet.mvc.AbstractController  其中的handleRequest()方法就是骨架方法，handleRequestInternal()就是需要子类各自实现的方法
 */
abstract class AbstractTemplate {

    /**
     * 在公共方法定义算法的骨架
     */
    public void commonOperation(){
        System.out.println("公共操作1");
        System.out.println("公共操作2");
        System.out.println("公共操作3");

        /*
        * 在抽象类里：可以在具体方法调用抽象方法；
        * 子类可以不改变父类公共操作方法结构的基础上通过下面的抽象方法自定义自己的方法
        */
        templateMethod();
    }

    /**
     * 抽象方法，由具体的子类自己实现，每个子类都不同
     */
    abstract void templateMethod();
}
