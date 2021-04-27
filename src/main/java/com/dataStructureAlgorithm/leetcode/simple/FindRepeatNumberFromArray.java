package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 需求：找出数组中重复的数字。
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
 * 也不知道每个数字重复了几次。请找出数组中《任意一个重复的数字》。
 * <p>
 * 示例 1：
 * 输入： [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 * 限制：2 <= n <= 100000
 */
public class FindRepeatNumberFromArray {

	@Test
	public void test() {
		int[] a = {2, 3, 1, 0, 2, 5, 3};
		int result = findRepeatNumber02(a);
		System.out.println(result);
	}

	/**
	 * 自己实现
	 *   时间复杂度：O(n^2)
	 */
	public int findRepeatNumber01(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = i; j < nums.length - 1; j++) {
				//j=0 比较6次
				//j=1 比较5次
				//......
				//j=6 比较1次
				if (nums[i] == nums[j + 1]) {
					return nums[i];
				}
			}
		}
		return 0;
	}


	/**
	 * 力扣实现
	 *     时间复杂度：O(n)
	 */
	public int findRepeatNumber02(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		int repeat = -1;
		for (int num : nums) {
			if (!set.add(num)) {
				repeat = num;
				break;
			}
		}
		return repeat;
	}


}
