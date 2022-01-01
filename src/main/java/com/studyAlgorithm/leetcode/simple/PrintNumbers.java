package com.studyAlgorithm.leetcode.simple;

import org.junit.Test;

import java.util.Arrays;

/**
 * 需求：输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。 
 *
 *  示例：
 *       输入: n = 1
 *       输出: [1,2,3,4,5,6,7,8,9]
 */
public class PrintNumbers {
	@Test
	public void test(){
		int[] ints = printNumbers(1);
		System.out.println(Arrays.toString(ints));
	}

	public int[] printNumbers(int n) {
		int[] arr = new int[(int) Math.pow(10, n) - 1];
		for(int i = 0; i < arr.length; i++){
			arr[i] = i + 1;
		}
		return arr;
	}
}
