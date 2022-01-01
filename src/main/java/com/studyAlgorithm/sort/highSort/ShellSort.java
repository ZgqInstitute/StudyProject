package com.studyAlgorithm.sort.highSort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序
 *      希尔排序是插入排序的改进，也称为缩小增量排序。
 */
public class ShellSort {

	@Test
	public void test() {
		int[] a = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};//{3, 5, 1, 6, 0, 8, 9, 4, 7, 2} —> {0, 2, 1, 4, 3, 5, 7, 6, 9, 8}
		sort(a);
	}

	public void sort(int[] array) {
		//控制分组的个数
		for (int len = array.length / 2; len > 0; len = len / 2) {

			for (int i = len; i < array.length; i++) {
				for (int j = i - len; j >= 0; j = j - len) {
					if (array[j] > array[j + len]) {
						int temp;
						temp = array[j];
						array[j] = array[j + len];
						array[j + len] = temp;
					} else
						break;
				}
			}
		}

		System.out.println(Arrays.toString(array));
	}

}
