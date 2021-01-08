package com.studyDesignPattern.decoratorDemo;

/**
 *
 *
 * */
public abstract class Drink {

    private String des;
    private float price;

    /**
     * 计算费用方法
     * */
    public abstract float cost();


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
