package com.studyAlgorithm.ArrayDemo;

/**
 * 求一个系列最长的子序列
 * 如：10，9，2，5，4，3，101，18
 * 最长子串：2,3,101
 */
public class MaxSub {

    public static void main(String[] args) {
        int[] a = new int[]{2, 5, 4, 3, 10, 7, 9, 15};
        System.out.println(searchSub(a));
    }

    public static int searchSub(int[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int tempMax = arr[i];
            int temp = 1;
            for (int j = i + 1; j < arr.length; j++) {
                if (tempMax < arr[j]) {
                    temp++;
                    tempMax = arr[j];
                }
            }
            max = max > temp ? max : temp;
        }

        return max;
    }
}
