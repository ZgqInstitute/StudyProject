package com.dataStructureAlgorithm.sort.highSort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序
 * 希尔排序是插入排序的改进，也称为缩小增量排序。
 */
public class ShellSort {

	//{8,9,1,7,2,3,5,4,6,0}
	@Test
	public void test() {
		int[] a = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};//{3, 5, 1, 6, 0, 8, 9, 4, 7, 2} —> {3, 5, 1, 6, 0, 8, 9, 4, 7, 2}
		sort(a);
	}

	//[1, 0, 5, 3, 4, 6, 2, 7, 8, 9]

	public void sort(int[] array) {

		for (int length = array.length / 2; length >= 1; length = length / 2) {
			for (int i = 0; i < array.length; i++) {
				if ((i + length) < array.length) {
					if (array[i] > array[i + length]) {
						int temp;
						temp = array[i];
						array[i] = array[i + length];
						array[i + length] = temp;
					}
				}else {
					break;
				}
			}
		}

		System.out.println(Arrays.toString(array));


	}


}
