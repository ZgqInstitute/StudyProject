package com.dataStructureAlgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 插入排序
 *
 */
public class InsertionSort {
	@Test
	public void test() {
		int[] a = {5, 11, 2, 6, 10, 8};//最坏时间复杂度例子：{20,16,10,8,6,3}
		// i = 1  {5, 11, 2, 6, 10, 8} —> {5, 11, 2, 6, 10, 8}
		// i = 2  {5, 11, 2, 6, 10, 8} —> {5, 11, 11, 6, 10, 8} —> {5, 5, 11, 6, 10, 8} —> {2, 5, 11, 6, 10, 8}
		sort(a);
	}

	public void sort(int[] array) {

		for (int i = 1; i < array.length; i++) {
			int insertVal = array[i];//n
			int insertIndex = i -1;//n

			while (insertIndex >= 0 && insertVal < array[insertIndex]) {
				//1  2  ... n     (1+n)*n = n^2 + n = n^2
				array[insertIndex + 1] = array[insertIndex];
				insertIndex -= 1;
			}

			array[insertIndex + 1] = insertVal;//n
		}

		System.out.println(Arrays.toString(array));
	}

}
