package com.studyFrame.springbootDemo.service.strategyAddFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Factory {
    private static Map<String, DiscountAlgorithmStrategy> strategyMap = new ConcurrentHashMap<>();

    /**
     * 不同的算法已经在spring启动时都加到map中了，这里可以直接从map中获取
     * 根据传进来的类型，拿到对应的策略
     */
    public static DiscountAlgorithmStrategy getStrategy(String discountType) {
        return strategyMap.get(discountType);
    }

    /**
     * 向map中注册不同的算法
     */
    public static void register(String discountType, DiscountAlgorithmStrategy strategyImpl) {
        if (discountType == null || strategyImpl == null) {
            return;
        }
        strategyMap.put(discountType, strategyImpl);
    }

}
