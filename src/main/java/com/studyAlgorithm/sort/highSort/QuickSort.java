package com.studyAlgorithm.sort.highSort;


import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

	public static void main(String[] args) {
		int[] a = new int[]{3, 2, 5, 7, 8, 1, 4, 9, 6};
		quickSort(a,0,a.length-1);
		System.out.println(Arrays.toString(a));
	}

	public static void quickSort(int[] arr, int left, int right){
		if(left > right){
			return;
		}

		int base = arr[left];
		int i = left;
		int j = right;

		while (i != j){
			//先从右边向左找，直到找到比基准数小的停止
			while (arr[j] >= base && i < j){
				j--;
			}
			//再从左边向右找，直到找到比基准数大的停止
			while (arr[i] <= base && i < j){
				i++;
			}
			//执行到这说明左面已经找到已经基准数小的数；右边找到了比基准数小的数；再交换
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		//执行到这说明i和j已经相遇，那么需要交换基准数和相遇位置的数
		arr[left] = arr[i];
		arr[i] = base;

		//执行到这，基准数的左边都比基准数小，基准数右边都比基准数大

		//再排序基准数左边
		quickSort(arr, left, i-1);
		//再排序基准数右边
		quickSort(arr, i+1, right);

	}
}
