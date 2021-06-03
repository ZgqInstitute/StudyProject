package com.dataStructureAlgorithm.queueDemo;

import org.junit.Test;

public class TestClass {

    @Test
    public void test(){
        System.out.println(isUnique("abc"));
    }

    public boolean isUnique(String astr) {
        char[] c = astr.toCharArray();
        for(int i = 0; i < c.length; i++){
            for(int j = i+1; j < c.length - i; j++){
                if(c[j] == c[i]){
                    return false;
                }
            }
        }
        return true;
    }
}
