package com.studyJDKAPI.studyJavaBase.generic.defineToSuperAndExtends;

public class Generic<T extends Number> {
    private T aa;

    public T getAa() {
        return aa;
    }

    public void setAa(T aa) {
        this.aa = aa;
    }
}
