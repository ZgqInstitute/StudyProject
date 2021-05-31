package com.dataStructureAlgorithm.stackDemo;

import org.junit.Test;

import java.util.Stack;

/**
 * 括号匹配问题
 * 判断一个字符串的括号是否成对出现
 * ( )(( ))   true
 * ( )(()     false
 * <p>
 * 解决思路：遍历字符串，当遍历到 ( 就放入栈中，当遍历到右括号就从栈中弹出一个 (
 */
public class BracketsMatch {
    @Test
    public void test() {
        String s = "(保罗(乔丹))()";
        Stack<String> stringStack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (String.valueOf(c).equals("(")) {
                stringStack.push(String.valueOf(c));
            }
            if (String.valueOf(c).equals(")")) {
                if (stringStack.isEmpty()) {
                    System.out.println("不匹配");
                    return;
                }
                stringStack.pop();
            }
        }
        if (!stringStack.isEmpty()) {
            System.out.println("不匹配");
        } else
            System.out.println("匹配");
    }
}
