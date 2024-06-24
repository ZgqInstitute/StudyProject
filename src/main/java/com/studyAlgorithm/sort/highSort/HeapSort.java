package com.studyAlgorithm.sort.highSort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 堆排序
 * 堆排序思路参考：https://www.bilibili.com/video/BV1fp4y1D7cj/?spm_id_from=333.788.recommend_more_video.1&vd_source=6236cc1d207e07a0bad6978dc5f9d845
 * n/2 - 1 表示最后一个堆的父节点
 */
public class HeapSort {

    @Test
    public void sort() {
        int[] a = {4, 2, 1, 7, 0, 3, 5, 8, 6, 9};
        System.out.println("排序前：" + Arrays.toString(a));
        heapSort(a);
        System.out.println("排序后：" + Arrays.toString(a));
    }

    private void heapSort(int[] a) {
        // 构建堆   a.length/2 - 1表示最后一个堆的父节点
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            heapifiy(a, a.length, i);
        }

        // 经过上面的构建堆，已经成型一个大顶堆

        // 排序
        for (int i = a.length - 1; i > 0; i--) {
            /*
             * 此时树的跟节点值是最大的：
             *   1-交换根节点与最后一个子节点
             *   2-交换完树就不满足大顶堆了，需要重新维护数组剩余的元素的平衡
             */
            int temp = a[i];
            a[i] = a[0];
            a[0] = temp;
            heapifiy(a, i, 0);
        }
    }

    /**
     * 维护堆。始终保持父节点大于子节点
     *
     * @param a    存储堆的数组
     * @param size 数组长度
     * @param i    待维护节点的下标
     */
    private void heapifiy(int[] a, int size, int i) {
        /*
         * 一颗树有父节点、左子节点、右子节点
         * max就是保存父节点、左子节点、右子节点最大数的索引下标
         */
        int max = i;
        int lson = i * 2 + 1;
        int rson = i * 2 + 2;

        if (lson < size && a[max] < a[lson]) {
            max = lson;
        }
        if (rson < size && a[max] < a[rson]) {
            max = rson;
        }

        // 若max!=i为true，说明有子节点的值比父节点的大，需要交换
        if (max != i) {
            int temp = a[i];
            a[i] = a[max];
            a[max] = temp;
            // 若上面是左子节点的值比父节点大，那么max就是左子节点的索引
            // 需要继续递归。左子节点
            heapifiy(a, size, max);
        }
    }

}
