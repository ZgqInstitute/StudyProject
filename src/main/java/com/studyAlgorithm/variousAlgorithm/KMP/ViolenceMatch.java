package com.studyAlgorithm.variousAlgorithm.KMP;

import org.junit.Test;

/**
 * 字符串匹配
 *  方法一：使用暴力匹配
 *
 *  字符串1："acNBXeNBAp";   字符串2："NBA"
 *  在字符串1中找到子串2，并返回字符串1的位置
 */
public class ViolenceMatch {

    @Test
    public void test(){
        String s1 = "acNBXeNBAp";
        String s2 = "NBA";
        int i = violeceMatch(s1, s2);
        System.out.println(i);
    }


    public int violeceMatch(String s1, String s2){
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        int c1Len = c1.length;//s1长度
        int c2Len = c2.length;//s2长度

        int i = 0;//指向s1
        int j = 0;//指向s2

        while (i < c1Len && j < c2Len){
            if(c1[i] == c2[j]){
                i++;
                j++;
            }else {
                i = i + 1 -j;
                j = 0;
            }
        }

        if(j == c2Len){
            return i - j;
        }else {
            return -1;
        }
    }
}
