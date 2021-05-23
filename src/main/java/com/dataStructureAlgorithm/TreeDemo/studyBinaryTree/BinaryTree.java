package com.dataStructureAlgorithm.TreeDemo.studyBinaryTree;

/**
 * 二叉树，根节点。到时候只要把设置好节点关系的二叉树根节点赋给root属性即可。
 */
public class BinaryTree {


    private int len;//二叉树节点的个数
    private Node root;//二叉树的根节点

    /**
     * 获取二叉树的节点个数
     *
     * @return 二叉树的节点个数
     */
    public int size() {
        return len;
    }

    /**
     * 向二叉树中插入元素
     */
    public void put(int data) {
        if (root == null) {
            len++;
            root = new Node(data, null, null);
        } else {
            put(root, data);
        }
    }

    public Node put(Node node, int data) {
        if (node == null) {
            len++;
            return new Node(data, null, null);
        }
        if (node.data > data) {
            node.left = put(node.left, data);
        } else if (node.data < data) {
            node.right = put(node.right, data);
        }
        return node;
    }

    /**
     * 判断二叉树中是否有某个元素
     *
     * @param target 目标元素
     * @return 是否存在
     */
    public boolean isExist(Node node, int target) {
        if(root == null){
            return false;
        }

        if (node == null || node.data == target) {
            if (node == null) {
                return false;
            }
            return true;
        }
        if (node.data < target) {
            return isExist(node.right, target);
        }

        if (node.data > target) {
            return isExist(node.left, target);
        }

        return false;
    }

    /**
     * 删除二叉树节点
     * 删除思路：删除指定节点后，要重新调整二叉树的平衡。要找删除节点右子树中最小的节点来替换被删除的节点
     */
    public void deleted() {

    }

    /**
     * 前序遍历
     */
    public int preOrder(Node node) {

        System.out.print(node.data + ", ");

        if (node.left != null) {
            preOrder(node.left);
        }
        if (node.right != null) {
            preOrder(node.right);
        }
        return -1;
    }

    /**
     * 中序遍历
     */
    public void infixOrder(Node node) {
        //递归向左子树中序遍历
        if (node.left != null) {
            infixOrder(node.left);
        }

        //先输出当前节点
        System.out.print(node.data + ", ");

        //递归向右子树中序遍历
        if (node.right != null) {
            infixOrder(node.right);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder(Node node) {
        //递归向左子树中序遍历
        if (node.left != null) {
            postOrder(node.left);
        }

        //递归向右子树中序遍历
        if (node.right != null) {
            postOrder(node.right);
        }

        //先输出当前节点
        System.out.print(node.data + ", ");
    }

    /**
     * 获取二叉树的最大深度
     */
    public int maxDepth(){
        return maxDepth(root);
    }

    public int maxDepth(Node node) {
        if (node == null) {
            return 0;
        }
        int maxLeft = 0;
        int maxRight = 0;
        if (node.left != null) {
            maxLeft = maxDepth(node.left);
        }
        if (node.right != null) {
            maxRight = maxDepth(node.right);
        }
        return maxLeft > maxRight ? maxLeft + 1 : maxRight + 1;
    }

    /**
     * 节点内部类，封装节点数据
     */
    public class Node {
        private int data;//节点数据
        private Node left;//左节点数据
        private Node right;//右节点数据

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
