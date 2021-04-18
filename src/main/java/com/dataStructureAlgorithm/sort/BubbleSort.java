package com.dataStructureAlgorithm.sort;

import org.junit.Test;

/**
 * 冒泡排序
 *      需求：将{7,2,5,1,8,9}进行排序，排序后为：{1,2,5,7,8,9}
 */
public class BubbleSort {

	@Test
	public void test() {
		int[] a = {7, 19, 5, 43, 8, 9};
//		int[] a = new int[]{7, 2, 5, 1, 8, 9};

		sort(a);
	}

	public void sort(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				int temp;
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
