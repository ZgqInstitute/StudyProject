package com.dataStructureAlgorithm.TreeDemo.studyBinaryTree;

/**
 * 节点
 */
public class PersonNode {
	private int no;
	private String name;
	private PersonNode left;
	private PersonNode right;

	public PersonNode(int no, String name) {
		this.no = no;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonNode getLeft() {
		return left;
	}

	public void setLeft(PersonNode left) {
		this.left = left;
	}

	public PersonNode getRight() {
		return right;
	}

	public void setRight(PersonNode right) {
		this.right = right;
	}

	/**
	 * 节点的前序遍历方法
	 */
	public void preOrder() {
		//先输出当前节点
		System.out.println(this);

		//递归向左子树前序遍历
		if (this.left != null) {
			this.left.preOrder();
		}

		//递归向右子树前序遍历
		if (this.right != null) {
			this.right.preOrder();
		}
	}

	/**
	 * 节点的中序遍历方法
	 */
	public void infixOrder() {

		//递归向左子树中序遍历
		if (this.left != null) {
			this.left.infixOrder();
		}

		//先输出当前节点
		System.out.println(this);

		//递归向右子树中序遍历
		if (this.right != null) {
			this.right.infixOrder();
		}
	}

	/**
	 * 节点的后序遍历方法
	 */
	public void postOrder() {

		//递归向左子树后序遍历
		if (this.left != null) {
			this.left.postOrder();
		}

		//递归向右子树后序遍历
		if (this.right != null) {
			this.right.postOrder();
		}

		//先输出当前节点
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "PersonNode{" +
				"no=" + no +
				", name='" + name + '\'' +
				'}';
	}
}
