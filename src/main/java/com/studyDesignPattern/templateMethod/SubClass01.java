package com.studyDesignPattern.templateMethod;

public class SubClass01 extends AbstractTemplate{

    @Override
    void templateMethod() {
        System.out.println("具体的子类实现01");
    }
}
