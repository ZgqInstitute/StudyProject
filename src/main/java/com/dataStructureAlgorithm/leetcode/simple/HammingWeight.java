package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

/**
 * 需求：请实现一个函数，输入一个整数（以二进制串形式），输出该数二进制表示中 1 的个数。
 *
 * 例如，把 9 表示成二进制是 1001，有 2 位是 1。因此，如果输入 9，则该函数输出 2。
 *
 * ==================================================================
 *
 * 总结：
 *     （1）将十进制转为二进制方法：String s = Integer.toBinaryString(x);
 *     （2）判断字符相等直接使用：if (c == '1')
 *
 * ==================================================================
 */
public class HammingWeight {
	@Test
	public void test(){
		int i = hammingWeight(8);
		System.out.println(i);

	}

	public int hammingWeight(int x) {

		//将十进制转为二进制方法：Integer.toBinaryString(x)
		String s = Integer.toBinaryString(x);
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '1') {
				result++;
			}
		}
		return result;
	}
}
