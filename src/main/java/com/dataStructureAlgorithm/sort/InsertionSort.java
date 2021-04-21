package com.dataStructureAlgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {
    @Test
    public void test() {
        int[] a = {5, 11, 2, 6, 10, 8};//最坏时间复杂度例子：{20,16,10,8,6,3}
        // i = 1  {5, 11, 2, 6, 10, 8} —> {5, 11, 2, 6, 10, 8}
        // i = 2  {5, 11, 2, 6, 10, 8} —> {5, 11, 11, 6, 10, 8} —> {5, 5, 11, 6, 10, 8} —> {2, 5, 11, 6, 10, 8}
        sort2(a);
    }

    //方法一
    public void sort1(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int insertVal = array[i];//n
            int insertIndex = i - 1;//n

            while (insertIndex >= 0 && insertVal < array[insertIndex]) {
                //1  2  ... n     (1+n)*n = n^2 + n = n^2
                array[insertIndex + 1] = array[insertIndex];
                insertIndex -= 1;
            }

            array[insertIndex + 1] = insertVal;//n
        }

        System.out.println(Arrays.toString(array));
    }

    //方法二
    public void sort2(int[] array) {
        //i=1 {5, 11, 2, 6, 10, 8}
        //i=2 {5, 11, 2, 6, 10, 8} —> {5, 2, 11, 6, 10, 8} —> {2, 5, 11, 6, 10, 8}
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                /* 最坏时间复杂度
                 * {6,5,4,3,2,1}
                 * 5 需要比较1次
                 * 4 需要比较2次
                 * 3 需要比较3次
                 * 2 需要比较4次
                 * 1 需要比较5次
                 *
                 * 需要比较和交换的次数都为：1+2+3+...+n —>  O(n^2)
                 */
                if (array[j] < array[j - 1]) {
                    int temp;
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                } else
                    break;
            }
        }

        System.out.println(Arrays.toString(array));
    }


}
