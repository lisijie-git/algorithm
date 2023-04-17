package com.lisijietech.algorithm.module.base.datastructure.binarytree;

/**
 * 二叉树节点。
 * 节点data不能为null，以免和顺序存储的null节点填充产生歧义，顺序存储用null填充表示空节点。
 * 所以有关data的初始化和设置都要校验null值。
 * 当然，data非要设置为null，可能有什么特殊需求，默认构造器还是可以满足的，而且getData方法没有校验。
 * 不过顺序存储的null节点填充就需要重新考虑。
 * @author lisijie
 * @param <T>
 * @date 2020-8-8
 */
public class BinaryTreeNode<T> {
	/*
	 * 节点泛型数据。数据不能为null值，以免和顺序存储的null填充产生歧义，顺序存储用null填充表示空节点。
	 */
	private T data;
	/*
	 * 节点左孩子节点
	 */
	private BinaryTreeNode<T> left;
	/*
	 * 节点右孩子节点
	 */
	private BinaryTreeNode<T> right;
	
	public BinaryTreeNode() {}
	
	public BinaryTreeNode(T data) {
		if(data == null) {
			throw new RuntimeException("二叉树节点的data数据不能为null");
		}
		this.data = data;
	}
	
	public BinaryTreeNode(T data,BinaryTreeNode<T> left,BinaryTreeNode<T> right) {
		if(data == null) {
			throw new RuntimeException("二叉树节点的data数据不能为null");
		}
		this.data = data;
		this.left = left;
		this.right = right;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		if(data == null) {
			throw new RuntimeException("二叉树节点的data数据不能为null");
		}
		this.data = data;
	}
	public BinaryTreeNode<T> getLeft() {
		return left;
	}
	public void setLeft(BinaryTreeNode<T> left) {
		this.left = left;
	}
	public BinaryTreeNode<T> getRight() {
		return right;
	}
	public void setRight(BinaryTreeNode<T> right) {
		this.right = right;
	}
}
