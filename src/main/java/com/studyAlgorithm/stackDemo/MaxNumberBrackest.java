package com.studyAlgorithm.stackDemo;

import java.util.Stack;

/**
 * 例如：
 * 1： (())输出2；
 * 2：(())() 输出2；
 */
public class MaxNumberBrackest {
    public static void main(String[] args) {
        String str = "(((((())))))))))))))))()()";
        int temp = 0;
        int maxCount = 0;//最多的括号数
        Stack stack = new Stack();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                if (i != 0 && str.charAt(i - 1) == ')') {
                    stack.clear();
                    stack.push(str.charAt(i));
                } else {
                    stack.push(str.charAt(i));
                }
            } else if (str.charAt(i) == ')' && !stack.empty()) {
                stack.pop();//取出栈顶元素，并删除
                temp++;
                maxCount = maxCount > temp ? maxCount : temp;
                if (i < str.length() -1 && str.charAt(i + 1) == '(') {
                    temp = 0;
                }
            }

        }

        System.out.println(maxCount);





//                if (!stack.empty()) {//判断栈是否为空
//                    stack.pop();//取出栈顶元素，并删除
//                    temp += 1;
//                    if (i == str.length() - 1) {
//                        maxCount = Math.max(maxCount, temp);
//                        temp = 0;
//                    }
//                    if (!(i == str.length() - 1) && str.charAt(i + 1) == '(') {
//                        maxCount = Math.max(maxCount, temp);
//                        temp = 0;
//                        stack.clear();//使用stack父类vector的clean()方法清除栈中全部元素；
//                    }
//                } else {
//                    maxCount = Math.max(maxCount, temp);
//                    temp = 0;
//                }
//            } else {
//                stack.push(str.charAt(i));
//            }
//        }
//        System.out.println(maxCount);
    }

}
