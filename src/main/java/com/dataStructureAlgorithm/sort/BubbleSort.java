package com.dataStructureAlgorithm.sort;

import org.junit.Test;

/**
 * 冒泡排序
 *      需求：将{7,2,5,1,8,9}进行排序，排序后为：{1,2,5,7,8,9}
 */
public class BubbleSort {

	@Test
	public void test() {
		int[] a = {6, 5, 4, 3, 2, 1};
//		int[] a = new int[]{7, 2, 5, 1, 8, 9};

		sort(a);
	}

	public void sort(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				int temp;
				/* 在最坏情况下需要排序的数组为：[6, 5, 4, 3, 2, 1]
				 * 6需要比较 6 - 1次 （n-1次）
				 * 5需要比较 6 - 2次 （n-2次）
				 * .....
				 * 2需要比较 6 - 5次 （n-5次）
				 *
				 * 总共需要比较的次数：(n-1)+(n-2)+(n-3)+(n-4)+...+1 = n^2/2 - n/2
				 *
				 * 总共需要交换的次数：(n-1)+(n-2)+(n-3)+(n-4)+...+1 = n^2/2 - n/2
				 *
				 * 总执行次数（冒泡算法的时间复杂度）：n^2 - n  —> O(n^2)
				 */
				if (array[j] > array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}

		for (int x = 0; x < array.length; x++) {
			System.out.print(array[x] + " ");
		}
	}
}
