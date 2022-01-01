package com.studyAlgorithm.leetcode.simple;

import org.junit.Test;

/**
 * 需求：把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，
 * 输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。 
 *
 *  示例：
 *       输入：[3,4,5,1,2]
 *       输出：1
 */
public class MinArray {

	@Test
	public void test(){
		int[] a = {2, 3, 4, 5, 6};
		int i = minArray(a);
		System.out.println(i);

	}

	public int minArray(int[] arr) {
		int max = arr[0];
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] <= arr[i + 1]) {
				max = arr[i + 1];
			}else {
				return arr[i+1];
			}
		}
		return arr[0];
	}

}
