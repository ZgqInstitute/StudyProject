package com.dataStructureAlgorithm.TreeDemo.studyBinaryTree;

import org.junit.Test;

public class BinaryTreeTestClass {

	@Test
	public void TestMethod() {
		//创建二叉树
		BinaryTree binaryTree = new BinaryTree();
		//创建节点
		PersonNode root = new PersonNode(1, "宋江");
		PersonNode node2 = new PersonNode(2, "吴用");
		PersonNode node3 = new PersonNode(3, "卢俊义");
		PersonNode node4 = new PersonNode(4, "林冲");

		//设置节点间的关系
		root.setLeft(node2);
		root.setRight(node3);
		node3.setRight(node4);

		System.out.println("前序遍历");
		binaryTree.setRoot(root);
		binaryTree.preOrder();

		System.out.println();
		System.out.println("中序遍历");
		binaryTree.infixOrder();

		System.out.println();
		System.out.println("后序遍历");
		binaryTree.postOrder();
	}
}
