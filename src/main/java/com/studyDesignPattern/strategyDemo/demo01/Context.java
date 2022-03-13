package com.studyDesignPattern.strategyDemo.demo01;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description: 使用策略的类
 */
public class Context {
    private Strategy strategy;

    public Context(){}

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(){
        strategy.draw();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
