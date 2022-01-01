package com.studyAlgorithm.leetcode.simple;

import org.junit.Test;


/**
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例：
 *    输入: [1, 2, 3, 2, 2, 2, 5, 4, 2]
 *     输出: 2
 */
public class MajorityElement {
	@Test
	public void test() {
		int[] a = {1, 2, 3, 2, 2, 2, 3, 4, 2, 3, 4, 3, 3, 3, 3, 3};
		int i = majorityElement(a);
		System.out.println(i);

	}

	public int majorityElement(int[] nums) {
		int len = nums.length / 2;
		int maxNum = 1;
		int num = 0;
		int temp = 1;
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] == nums[j]) {
					temp++;
				}
			}
			if (maxNum < temp) {
				if(temp > len){
					num = nums[i];
					return num;
				}
				maxNum = temp;
				num = nums[i];
			}
		}
		return num;
	}
}
