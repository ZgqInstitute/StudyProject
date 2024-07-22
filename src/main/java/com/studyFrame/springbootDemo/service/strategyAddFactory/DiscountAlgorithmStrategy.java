package com.studyFrame.springbootDemo.service.strategyAddFactory;

import org.springframework.beans.factory.InitializingBean;

public interface DiscountAlgorithmStrategy extends InitializingBean {

    /**
     * 这个就是将不同算法抽象出来，由具体的算法自己实现
     */
    String discount(String algParam);

}
