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
		int[] a = {5, 11, 2, 6, 10, 8};
		// i = 1  {5, 11, 2, 6, 10, 8} —> {5, 11, 2, 6, 10, 8}
		// i = 2  {5, 11, 2, 6, 10, 8} —> {5, 11, 11, 6, 10, 8} —> {5, 5, 11, 6, 10, 8} —> {2, 5, 11, 6, 10, 8}
		sort(a);
	}

	public void sort(int[] array) {

		for (int i = 1; i < array.length; i++) {
			int insertVal = array[i];
			int insertIndex = i -1;

			while (insertIndex >= 0 && insertVal < array[insertIndex]) {
				array[insertIndex + 1] = array[insertIndex];
				insertIndex -= 1;
			}

			array[insertIndex + 1] = insertVal;//
		}

		System.out.println(Arrays.toString(array));
	}

}
