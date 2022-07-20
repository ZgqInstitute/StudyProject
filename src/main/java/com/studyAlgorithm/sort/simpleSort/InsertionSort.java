package com.studyAlgorithm.sort.simpleSort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 插入排序。可以理解为扑克牌，手上的排都是拍好序的，抓了一张新牌需要按顺序插入手中的牌
 * 先拿第2个数与前面的数进行比较；
 * 再拿第3个数与前面的数进行比较；
 * 再拿第4个数与前面的数进行比较；
 * .......
 *
 *
 * 案例：假如原数组为arr[9, 6, 1, 3, 5]
 *       假如第一个位置已经排好序；
 *       先拿arr[1]与arr[0]比较，小的放前面，变为：arr[6, 9, 1, 3, 5]
 *
 *       现在前2位已经有序
 *       再拿arr[2]跟前面的进行比较，变为：arr[1, 6, 9, 3, 5]
 *
 *       现在前3位已经有序
 *       再拿arr[3]与前面进行比较，变为：arr[1, 3, 6, 9, 5]
 *
 *       现在前4位已经有序
 *       再拿arr[4]与前面进行比较，变为：arr[1, 3, 5, 6, 9]
 */
public class InsertionSort {
    @Test
    public void test() {
        int[] a = {5, 11, 2, 6, 10, 8};//最坏时间复杂度例子：{20,16,10,8,6,3}
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
        // 一开始就拿第二个数跟前面的数做比较
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
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
