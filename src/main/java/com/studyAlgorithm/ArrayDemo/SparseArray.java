package com.studyAlgorithm.ArrayDemo;


import org.junit.Test;

public class SparseArray {

	@Test
	public void testMethod() {

		//定义普通二维数组
//		int[][] commonArray = {
//				{0, 12, 2, 0},
//				{0, 3, 0, 0},
//				{3, 0, 1, 0},
//				{0, 0, 0, 9},
//				{0, 0, 0, 0},
//				{0, 0, 0, 0},
//		};

		//定义普通二维数组
		int[][] sparseArray = {
				{6, 4, 6},
				{0, 1, 12},
				{0, 2, 2},
				{1, 1, 3},
				{2, 0, 3},
				{2, 2, 1},
				{3, 3, 9},
		};


		/* 上面普通二维数组对应的稀疏数组如下：
		     [6, 4, 6]
		     [0, 1, 12]
		     [0, 2, 2]
		     [1, 1, 3]
		     [2, 0, 3]
		     [2, 2, 1]
		     [3, 3, 9]
		*/

		int[][] commonArray = sparseToCommonArray(sparseArray);

		/*
		 * 遍历稀疏数组
		 */
		for (int a = 0; a < commonArray.length; a++) {
			for (int b = 0; b < commonArray[a].length; b++) {
				if (b != commonArray[a].length - 1) {
					System.out.print(commonArray[a][b]+", ");
				} else {
					System.out.println(commonArray[a][b]);

				}
			}
		}


	}

	/**
	 * 普通数组转稀疏数组
	 */
	public int[][] commonToSparseArray(int[][] commonArray) {
		//记录非0元素的个数
		int noNullNum = 0;

		/*
		 * commonArray.length是获取二维数组的行数（二维数组.length是获取二维数组的行数）
		 * 这个嵌套for循环是得到普通二维数组中非0元素的个数
		 */
		for (int i = 0; i < commonArray.length; i++) {
			for (int j = 0; j < commonArray[i].length; j++) {
				if (commonArray[i][j] != 0) {
					noNullNum++;
				}
			}
		}

		//普通二维数组的行数
		int hang = commonArray.length;//hang = 7
		//普通二维数组的列数
		int lie = commonArray[0].length;//lie = 4

		//定义稀疏数组  稀疏数组的行数=普通数组非0元素个数+1   稀疏数组的列数=3
		int[][] sparseArray = new int[noNullNum + 1][3];

		sparseArray[0][0] = hang;
		sparseArray[0][1] = lie;
		sparseArray[0][2] = noNullNum;

		int sparseArrayHang = 0;
		for (int i = 0; i < commonArray.length; i++) {
			for (int j = 0; j < commonArray[i].length; j++) {
				if (commonArray[i][j] != 0) {
					sparseArrayHang++;
					sparseArray[sparseArrayHang][0] = i;
					sparseArray[sparseArrayHang][1] = j;
					sparseArray[sparseArrayHang][2] = commonArray[i][j];
				}
			}
		}

		return sparseArray;
	}

	/**
	 * 稀疏数组转普通数组
	 */
	public int[][] sparseToCommonArray(int[][] sparseArray) {

		int commonHang = sparseArray[0][0];
		int commonLie = sparseArray[0][1];

		int[][] commonArray = new int[commonHang][commonLie];

		for (int i = 1; i < sparseArray.length; i++) {
			commonArray[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
		}

		return commonArray;
	}

}
