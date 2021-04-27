package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

/**
 * 需求：请实现一个函数，把字符串 s 中的每个空格替换成"%20"
 *  示例：
 *       输入：s = "We are happy."
 *       输出："We%20are%20happy."
 * ===================================================
 *
 * 总结：
 *       （1）获取字符串指定位置的字符方法：char c = s.charAt(i);
 *       （2） String str2 = new String(c);// 将整个字符数组转换为字符串
 *             String str3 = new String(c, 0, 3);// 将部分字符数组转换为字符串
 *
 * ====================================================
 */
public class ReplaceSpace {

	@Test
	public void test(){
		String s = "We are happy.";
		String s1 = replaceSpace(s);
		System.out.println(s1);
	}

	public String replaceSpace(String s){
		int length = s.length();
		System.out.println(length);
		//创建字符数组。假设s字符串每一个位置的字符都是空格，那么字符数组的长度为3*length
		char[] array = new char[length * 3];
		System.out.println(array.length);
		//这个size表示字符数组array实际存放的大小
		int size = 0;
		for (int i = 0; i < length; i++) {
			//获取字符串s索引i位置的字符
			char c = s.charAt(i);
			//判断字符串s索引i位置的字符是否为 空格
			if (c == ' ') {
				array[size++] = '%';
				array[size++] = '2';
				array[size++] = '0';
			} else {
				array[size++] = c;
			}
		}

		//将字符数组转为字符串
		String newStr = new String(array, 0, size);
		return newStr;
	}
}
