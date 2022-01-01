package com.studyAlgorithm.leetcode.secondary;

import org.junit.Test;

/**
 *  需求：在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 *       请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class FindNumberIn2DArray {

    @Test
    public void test(){
        int[][] arr = {{5},{6}};

        boolean numberIn2DArray = findNumberIn2DArray(arr, 6);
        System.out.println(numberIn2DArray);
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix.length==0 || matrix[0].length==0){
            return false;
        }
        int hang = matrix.length;
        int lie = matrix[0].length;
        //判断目标数是否在二维数组中
        if(target < matrix[0][0] && target > matrix[hang-1][lie-1]){
            return false;
        }

        for(int h = 0; h < hang; h++){
            if(target > matrix[h][lie-1]){
                continue;
            }
            for(int l = 0; l < lie; l++){
                if(target == matrix[h][l]){
                    return true;
                }
            }
        }
        return false;
    }
}
