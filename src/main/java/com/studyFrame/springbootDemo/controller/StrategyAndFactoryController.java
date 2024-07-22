package com.studyFrame.springbootDemo.controller;

import com.studyFrame.springbootDemo.service.strategyAddFactory.DiscountAlgorithmStrategy;
import com.studyFrame.springbootDemo.service.strategyAddFactory.Factory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示：使用策略模式+工厂模式，实现多种算法的随意切换
 */
@RestController
@RequestMapping("/strategy")
public class StrategyAndFactoryController {

    @GetMapping("/factory")
    public String update(@RequestParam("type") String type){
        // 根据类型拿到算法策略
        DiscountAlgorithmStrategy strategy = Factory.getStrategy(type);
        // 调用对应的算法策略
        return strategy.discount("我是算法的参数");
    }


}
