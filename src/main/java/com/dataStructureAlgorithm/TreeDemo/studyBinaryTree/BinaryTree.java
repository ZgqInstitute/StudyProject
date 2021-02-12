package com.dataStructureAlgorithm.TreeDemo.studyBinaryTree;

/**
 * 二叉树，根节点。到时候只要把设置好节点关系的二叉树根节点赋给root属性即可。
 */
public class BinaryTree {
	private PersonNode root;

	public void setRoot(PersonNode root) {
		this.root = root;
	}

	//前序遍历
	public void preOrder(){
		if(this.root != null){
			this.root.preOrder();
		}else {
			System.out.println("二叉树为空");
		}
	}

	//中序遍历
	public void infixOrder(){
		if(this.root != null){
			this.root.infixOrder();
		}else {
			System.out.println("二叉树为空");
		}
	}

	//后序遍历
	public void postOrder(){
		if(this.root != null){
			this.root.postOrder();
		}else {
			System.out.println("二叉树为空");
		}
	}
}
