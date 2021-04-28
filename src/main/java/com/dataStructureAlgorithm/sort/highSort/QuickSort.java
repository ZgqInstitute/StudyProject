package com.dataStructureAlgorithm.sort.highSort;

import org.junit.Test;
import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

	@Test
	public void test() {
		int[] a = {6, 4, 5, 7, 1, 3, 2, 8};
		System.out.println("排序前：" + Arrays.toString(a));
		quickSort(a);
		System.out.println("排序后：" + Arrays.toString(a));
	}

	/**
	 * 对arr数组所有元素进行排序
	 */
	public void quickSort(int[] arr) {
		int low = 0;
		int high = arr.length - 1;
		quickSort(arr, low, high);
	}

	/**
	 * 对arr数组部分元素进行排序
	 */
	private void quickSort(int[] arr, int low, int high) {//???递归何时结束
		if (low < high) {
			//分区操作，将一个数组分成两个分区，返回分区界限索引
			//partition(arr, 0, 7);

			int index = partition(arr, low, high);
			//对左分区进行快排
			quickSort(arr, low, index - 1);
			//对右分区进行快排
			quickSort(arr, index + 1, high);
		}
	}

	private int partition(int[] arr, int low, int high) {
		//指定左指针i和右指针j
		int left = low;//指向左子组数组的第一个元素
		int right = high;//指向右子组数组的最后一个元素

		//将第一个数作为基准值。挖坑
		int x = arr[low];

		//使用循环实现分区操作
		while (left < right) {
			//1.从右向左移动right，找到小于基准值(arr[low])的值: arr[right]
			while (arr[right] >= x && left < right) {
				right--;
			}
			//2.将右侧找到小于基准数的值加入到左边的（坑）位置， 左指针向中间移动一个位置left++
			if (left < right) {
				arr[left] = arr[right];
				left++;
			}
			//3.从左向右移动left，找到大于等于基准值(arr[low])的值arr[left]
			while (arr[left] < x && left < right) {
				left++;
			}
			//4.将左侧找到的打印等于基准值的值加入到右边的坑中，右指针向中间移动一个位置 right--
			if (left < right) {
				arr[right] = arr[left];
				right--;
			}
		}

		//使用基准值填坑，这就是基准值的最终位置
		arr[left] = x;//arr[right] = y;
		//返回基准值的位置索引
		return left; //return right;
	}

}
