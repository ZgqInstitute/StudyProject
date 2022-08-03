package com.studyAlgorithm.TreeDemo.studyBinaryTree;

import org.junit.Test;

public class BinaryTreeTestClass {

	@Test
	public void TestMethod() {
		//创建二叉树
		BinaryTree binaryTree = new BinaryTree();
		binaryTree.put(10);
		binaryTree.put(6);
		binaryTree.put(12);
		binaryTree.put(3);
		binaryTree.put(7);


		binaryTree.preOrder( binaryTree.getRoot());
		System.out.println();
		binaryTree.infixOrder(binaryTree.getRoot());
		System.out.println(binaryTree.maxDepth());

		System.out.println(binaryTree.isExist(binaryTree.getRoot(),122));

	}
}
