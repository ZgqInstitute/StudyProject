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
		int[] a = {7, 5, 12, 4, 10, 6, 3};//{6, 5, 4, 3, 2, 1}
		sort(a);
	}

	private void sort(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				int temp;
				if (array[i] > array[j]) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}

		System.out.print(Arrays.toString(array));

	}


}
