package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

import java.util.Arrays;

/**
 * 需求：输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
 *
 * 输入：nums = [1,2,3,4]
 * 输出：[1,3,2,4]
 * 注：[3,1,2,4] 也是正确的答案之一。
 *
 */
public class Exchange {

	@Test
	public void test(){
		int[] a = {2, 3, 4, 5, 6, 1, 1, 13, 21, 54, 66, 88, 99};
		int[] i = ecchange(a);
		System.out.println(Arrays.toString(i));

	}

	public int[] ecchange(int[] nums) {
		int[] result = new int[nums.length];
		int left = 0;
		int right = nums.length - 1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] % 2 == 0) {
				result[right] = nums[i];
				right--;
			}else {
				result[left] = nums[i];
				left++;
			}
		}
		return result;
	}
}
