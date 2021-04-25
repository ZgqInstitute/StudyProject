package com.dataStructureAlgorithm.sort.highSort;

import org.junit.Test;

/**
 * 归并排序
 */
public class MergeSort {
	@Test
	public void test() {
		int[] a = {26, 5, 98, 108, 28, 99, 100, 56, 34, 1};
		printArray("排序前：", a);
		MergeSort(a);
		printArray("排序后：", a);
	}

	private void printArray(String pre, int[] a) {
		System.out.print(pre + "\n");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println();
	}

	//对数组中所有元素进行排序
	private void MergeSort(int[] a) {
		// TODO Auto-generated method stub
		System.out.println("开始排序");
		//调用sort()方法对数组a的部分进行排序
		Sort(a, 0, a.length - 1);
	}

	//对数组从left到right的元素进行排序
	private void Sort(int[] a, int left, int right) {
		if (left >= right)
			return;

		//定义分组的中间值
		int mid = (left + right) / 2;

		//二路归并排序就将原数组分为2个组。（注：多路归并排序里面写多个Sort就可以了）
		Sort(a, left, mid);//一个组
		Sort(a, mid + 1, right);//另一个组

		//再对2个组进行归并
		merge(a, left, mid, right);
	}

	/**
	 * 合并方法
	 * 数组a从索引left到索引mid为一个子组，索引mid+1到索引right为另一个分组；
	 * 把数组a中的这两个子组的数据合并成一个有序的大组（从索引left到索引right）
	 */
	private void merge(int[] a, int left, int mid, int right) {
		//完成归并操作需要的辅助数组
		int[] tmp = new int[a.length];

		int r1 = mid + 1;
		int tIndex = left;
		int cIndex = left;
		// 逐个归并
		while (left <= mid && r1 <= right) {
			if (a[left] <= a[r1])
				tmp[tIndex++] = a[left++];
			else
				tmp[tIndex++] = a[r1++];
		}
		// 将左边剩余的归并
		while (left <= mid) {
			tmp[tIndex++] = a[left++];
		}
		// 将右边剩余的归并
		while (r1 <= right) {
			tmp[tIndex++] = a[r1++];
		}

		//System.out.println("第" + (++number) + "趟排序:\t");
		//TODO Auto-generated method stub
		//从临时数组拷贝到原数组
		while (cIndex <= right) {
			a[cIndex] = tmp[cIndex];
			//输出中间归并排序结果
			System.out.print(a[cIndex] + "\t");
			cIndex++;
		}
		System.out.println();
	}



}

