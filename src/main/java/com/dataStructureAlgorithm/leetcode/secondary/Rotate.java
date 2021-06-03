package com.dataStructureAlgorithm.leetcode.secondary;

import org.junit.Test;

/**
 * 旋转数组
                  给定 matrix =
                  [1,2,3],
                  [4,5,6],
                  [7,8,9]

                  原地旋转输入矩阵，使其变为:
                  [7,4,1],
                  [8,5,2],
                  [9,6,3]
 *
 *
 */
public class Rotate {

    @Test
    public void test(){
        int[][] arr = {{1,2,3},{4,5,6},{7,8,9}};
        printArray(arr);

        rotate(arr);


    }


   /*
      [1, 2, 3]
      [4, 5, 6]
      [7, 8, 9]
   */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                //i=0  j=0  temp=matrix[0][0]
                //i=0  j=1  temp=matrix[0][1]
                int temp = matrix[i][j];
                //matrix[0][0]=matrix[2][0]
                //matrix[0][1]=matrix[1][0]
                matrix[i][j] = matrix[n - j - 1][i];
                //matrix[2][0]=matrix[2][2]
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                //matrix[2][2]=matrix[0][2]
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                //matrix[0][2]=matrix[0][0]
                matrix[j][n - i - 1] = temp;
            }
        }

        printArray(matrix);
    }


    public void printArray(int[][] arr){
        for(int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[i].length; j++) {
                if (j < arr[i].length - 1) {
                    System.out.print(arr[i][j]+",");
                }else {
                    System.out.println(arr[i][j]);
                }

            }
        }
        System.out.println();
    }





















}
