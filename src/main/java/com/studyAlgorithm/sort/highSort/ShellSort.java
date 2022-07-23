package com.studyAlgorithm.sort.highSort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序
 *      希尔排序是插入排序的改进，也称为缩小增量排序。
 *      排序思路参考：https://www.bilibili.com/video/BV1n4411U7BD?p=64&vd_source=6236cc1d207e07a0bad6978dc5f9d845
 */
public class ShellSort {

	@Test
	public void test() {
		int[] a = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
		sort(a);
	}

	public void sort(int[] array) {
		//控制分组的个数。每组采用插入排序的方式进行排序。直到len=1最后一组，就是插入排序
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
