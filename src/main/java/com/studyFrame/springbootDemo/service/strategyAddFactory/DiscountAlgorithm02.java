package com.studyFrame.springbootDemo.service.strategyAddFactory;

import org.springframework.stereotype.Component;

@Component
public class DiscountAlgorithm02 implements DiscountAlgorithmStrategy{
    @Override
    public String discount(String algParam) {
        return "使用第二种优惠策略，参数：" + algParam;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("type2", this);
    }
}
