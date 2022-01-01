package com.studyAlgorithm.leetcode.simple;

import org.junit.Test;

import java.util.Arrays;


/**
 * 需求：输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
 * 示例：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * [1, 2, 3]
 * [4, 5, 6]
 * [7, 8, 9]
 * 输出：[1,2,3,6,9,8,7,4,5]
 */
public class SpiralOrder {

	@Test
	public void test() {
		int[][] arr = {
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
				{13, 14, 15, 16}
		};
		int[] ints = spiralOrder(arr);
		System.out.println(Arrays.toString(ints));
	}


	public int[] spiralOrder(int[][] matrix) {
		if (matrix.length == 0)
			return new int[0];

		int l = 0;//左边界
		int r = matrix[0].length - 1;//r=2右边界
		int t = 0;//上边界
		int b = matrix.length - 1;//b=2下边界
		int x = 0;

		//定义一个一维数组，用于存储结果
		int[] res = new int[(r + 1) * (b + 1)];

		/**
		 *  matrix [1,  2,  3,   4]       order [1, 2, 3,  ,  ,  ,  ,  ,  ]
		 *         [5,  6,  7,   8]
		 *         [9,  10, 11, 12]
		 *         [13, 14, 15, 16]
		 */
		while (true) {
			//从左往右，列在变，列为循环值，从左往右的下一步是往下走，上边界收缩，故++t
			for (int i = l; i <= r; i++) {
				res[x++] = matrix[t][i];// 从左往右
			}
			if (++t > b) break;
			//从上往下，行在变，从上往下的下一步是从右往左，右边界收缩，--r
			for (int i = t; i <= b; i++) {
				res[x++] = matrix[i][r]; // 从上往下
			}
			if (l > --r) break;
			//从右向左，列在变，从右往左的下一步是从下往上，下边界收缩，--b
			for (int i = r; i >= l; i--){
				res[x++] = matrix[b][i]; // 从右往左
			}
			if (t > --b) break;
			//从下到上，行在变，从下到上的下一步是从左到右，左边界收缩，++l
			for (int i = b; i >= t; i--) {
				res[x++] = matrix[i][l]; // 从下往上
			}
			if (++l > r) break;
		}
		return res;
	}

}
