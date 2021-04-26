package com.dataStructureAlgorithm.sort.highSort;

import org.junit.Test;

/**
 * 归并排序
 */
public class MergeSort {
	@Test
	public void test() {
		int[] a = {8, 4, 5, 7, 1, 3, 6, 2};
		printArray("排序前：", a);
		mergeSort(a);
		printArray("排序后：", a);
	}

	//对数组中所有元素进行排序
	private void mergeSort(int[] a) {
		System.out.println("开始排序");
		//调用sort()方法对数组a的部分进行排序
		sort(a, 0, a.length - 1);
	}

	//对数组从left到right的元素进行排序
	private void sort(int[] a, int left, int right) {
		if (left >= right)
			return;

		//定义分组的中间值
		int mid = (left + right) / 2;

		//二路归并排序就将原数组分为2个组。（注：多路归并排序里面写多个Sort就可以了）
		//向左递归进行分解
		sort(a, left, mid);//一个组
		//向右递归进行分解
		sort(a, mid + 1, right);//另一个组

		//再对2个组进行归并
		merge(a, left, mid, right);
	}

	/**
	 * 合并方法
	 * 数组a从索引left到索引mid为一个子组，索引mid+1到索引right为另一个分组；
	 * 把数组a中的这两个子组的数据合并成一个有序的大组（从索引left到索引right）
	 */
	private void merge(int[] a, int left, int mid, int right) {
		//完成归并操作需要的辅助数组，需要额外的空间。以空间换时间
		int[] tmp = new int[a.length];

		//定义3个指针
		int i = left;//指向辅助数组的第一个索引处
		int p1 = left;//指向左子组的第一个索引
		int p2 = mid + 1;//指向右子组的第一个索引

		//逐个归并
		while (left <= mid && p2 <= right) {
			if (a[left] <= a[p2]) {
				tmp[p1] = a[left];
				p1 = p1 + 1;
				left = left + 1;
			}
			else {
				tmp[p1] = a[p2];
				p1 = p1 + 1;
				p2 = p2 + 1;
			}
		}
		//判断左子组是否全部遍历完，若左子组没有遍历完则顺序的将左子组剩余的元素移动到辅助数组对应的索引处
		while (left <= mid) {
			tmp[p1] = a[left];
			p1 = p1 + 1;
			left = left + 1;
		}
		//判断右子组是否全部遍历完，若右子组没有遍历完则顺序的将右子组剩余的元素移动到辅助数组对应的索引处
		while (p2 <= right) {
			tmp[p1] = a[p2];
			p1 = p1 + 1;
			p2 = p2 + 1;
		}

		//将辅助数组拷贝到原数组
		while (i <= right) {
			a[i] = tmp[i];
			//输出中间归并排序结果
			System.out.print(a[i] + "\t");
			i++;
		}

		System.out.println();
	}

	private void printArray(String pre, int[] a) {
		System.out.print(pre + "\n");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println();
	}

}

