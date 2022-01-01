package com.studyAlgorithm.leetcode.simple;

import org.junit.Test;

/**
 * 判断是否为字符重排
 * 输入: s1 = "abc", s2 = "bca"
 * 输出: true
 * <p>
 * 输入: s1 = "abc", s2 = "bad"
 * 输出: false
 */
public class CheckPermutation {

    @Test
    public void test() {
        String s1 = "abcabbbbb";
        String s2 = "cba";
        System.out.println(checkPermutation(s1, s2));
    }

    private boolean checkPermutation(String s1, String s2) {
        StringBuffer stringBuffer = new StringBuffer(s1);
        for (int i = 0; i < s2.length(); i++) {
            if (!s1.contains(s2.charAt(i) + "")) {
                return false;
            }
            String s = new String(stringBuffer);
            if (s.contains(s2.charAt(i) + "")) {
                stringBuffer.deleteCharAt(stringBuffer.indexOf(s2.charAt(i) + ""));
            } else {
                return false;
            }
        }

        if (stringBuffer.length() == 0) {
            return true;
        }
        return false;
    }

}
