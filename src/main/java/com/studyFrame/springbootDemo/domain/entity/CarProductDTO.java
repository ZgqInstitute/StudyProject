package com.studyFrame.springbootDemo.domain.entity;

import lombok.Data;

@Data
public class CarProductDTO {
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 车型
     */
    private String carModel;
    /**
     * 预估价打车费
     */
    private Integer money;


}
