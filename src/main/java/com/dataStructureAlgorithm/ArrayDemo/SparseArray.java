package com.dataStructureAlgorithm.ArrayDemo;


import org.junit.Test;

public class SparseArray {

	@Test
	public void testMethod() {

		int[][] commonArray = {
				{0, 0, 2, 0},
				{0, 0, 0, 0},
				{3, 0, 1, 0},
				{0, 0, 0, 9},
				{0, 7, 0, 0},
				{0, 7, 0, 0},
				{0, 7, 0, 0}
		};

		int[][] sparseArray = commonToSparseArray(commonArray);

		for (int a = 0; a < sparseArray.length; a++) {
			for (int b = 0; b < sparseArray[a].length; b++) {
				if (b != sparseArray[a].length - 1) {
					System.out.print(sparseArray[a][b]);
				} else {
					System.out.println(sparseArray[a][b]);

				}
			}
		}


	}

	/**
	 * 普通数组转稀疏数组
	 */
	public int[][] commonToSparseArray(int[][] commonArray) {
		int noNullNum = 0;
		for (int i = 0; i < commonArray.length; i++) {
			for (int j = 0; j < commonArray[i].length; j++) {
				if (commonArray[i][j] != 0) {
					noNullNum++;
				}
			}
		}

		int hang = commonArray.length;
		int lie = commonArray[0].length;
		int[][] sparseArray = new int[hang + 1][lie];
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
					sparseArray[sparseArrayHang][2] = sparseArray[i][j];
				}
			}
		}

		return sparseArray;
	}

	/**
	 * 稀疏数组转普通数组
	 */
	public int[][] SparseToCommonArray(int[][] sparseArray) {


		return null;
	}


}
