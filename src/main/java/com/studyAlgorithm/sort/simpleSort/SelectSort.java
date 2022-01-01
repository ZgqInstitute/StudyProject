package com.studyAlgorithm.sort.simpleSort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 选择排序：
 *         先拿第一个数去跟后面的每一个数进行比较，若发现有比第一个元素小的就交换位置，保证第一个位置是最小的数；
 *         再拿第二个数去跟后面的每一个数进行比较，若发现有比第二个元素小的就交换位置，保证第二个位置是第2小的数；
 *          .....
 */
public class SelectSort {

	@Test
	public void test() {
		int[] a = {66, 5, 12, 4, 10, 6};//{6, 5, 4, 3, 2, 1}
		sort(a);
	}

	public void sort(int[] array) {

		for (int i = 0; i < array.length - 1; i++) {
			int minIndex = i;
			int min = array[i];

			for (int j = i + 1; j < array.length; j++) {

				/* 时间复杂度
				 * 假如需要排序的队列为：{6, 5, 4, 3, 2, 1}
				 * 6需要比较 6 - 1次 （n-1次）
				 * 5需要比较 6 - 2次 （n-2次）
				 * .....
				 * 2需要比较 6 - 5次 （n-5次）
				 *
				 * 总共需要比较的次数：(n-1)+(n-2)+(n-3)+(n-4)+...+1 = n^2/2 - n/2
				 *
				 * 总共需要交换的次数：n -1
				 *
				 * 总执行次数（排序算法的时间复杂度）： O(n^2)
				 */
				if (min > array[j]) {
					min = array[j];
					minIndex = j;
				}
			}
			if (minIndex != i) {
				array[minIndex] = array[i];
				array[i] = min;
			}
		}

		System.out.print(Arrays.toString(array));

	}


}
