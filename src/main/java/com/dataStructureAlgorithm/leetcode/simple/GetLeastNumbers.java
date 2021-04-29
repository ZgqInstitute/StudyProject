package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

import java.util.Arrays;

/**
 * 需求：输入整数数组arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 * 10, 20, 30, 40
 */
public class GetLeastNumbers {

	@Test
	public void test() {
		int[] a = {4, 5, 1, 6, 2, 7, 3, 8};
		int[] leastNumbers = getLeastNumbers(a, 2);
		System.out.println(Arrays.toString(leastNumbers));
	}

	public int[] getLeastNumbers(int[] arr, int k) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp;
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		int[] result = new int[k];
		for (int x = 0; x < k; x++) {
			result[x] = arr[x];
		}
		return result;
	}

}
