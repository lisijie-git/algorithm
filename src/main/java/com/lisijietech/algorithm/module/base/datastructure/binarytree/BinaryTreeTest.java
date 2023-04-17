package com.lisijietech.algorithm.module.base.datastructure.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树相关测试
 * @author lisijie
 * @date 2020-8-21
 */
public class BinaryTreeTest {
	
	public static void main(String[] args) {
		//前序、中序、后序、层序列表
		List<String> preOrder = createPreOrder();
		List<String> inOrder = createInOrder();
		List<String> postOrder = null;
		List<String> levelOrder = null;
		//前序、层序的顺序存储结构列表。中序和后序顺序存储结构暂时没必要实现，只实现简单容易操作的。
		List<String> preOrderComplete = null;
		//List<String> inOrderComplete = null;
		//List<String> postOrderComplete = null;
		List<String> levelOrderComplete = null;
		//链式存储结构，根节点
		BinaryTreeNode<String> root = null;
		
		//通过前序和中序创建链式二叉树
		root = BinaryTree.createByPreInRecursion(preOrder, inOrder);
		System.out.println("通过前序、中序生成二叉树========================");
		System.out.println("前序：" + preOrder.toString());
		System.out.println("中序：" + inOrder.toString());
		//遍历二叉树。
		traversalTreePrintln(root);
		
		//二叉树转换为顺序存储结构
		preOrderComplete = BinaryTree.convertToCompletePre(root);
		levelOrderComplete = BinaryTree.convertToCompleteLevel(root);
		System.out.println("链式存储结构转换为顺序存储结构========================");
		System.out.println("前序存储：" + preOrderComplete);
		System.out.println("层序存储：" + levelOrderComplete);
		
		//通过顺序存储结构列表生成链式结构二叉树
		System.out.println("层序存储生成的链式二叉树========================");
		System.out.println("递归方法生成的");
		root = BinaryTree.createByCompleteLevelRecursion(levelOrderComplete);
		traversalTreePrintln(root);
		System.out.println("迭代方法生成的");
		root = BinaryTree.createByCompleteLevelRecursion(levelOrderComplete);
		traversalTreePrintln(root);
		System.out.println("前序存储生成的链式二叉树========================");
		System.out.println("递归方法生成的");
		root = BinaryTree.createByCompletePreRecursion(preOrderComplete);
		traversalTreePrintln(root);
		System.out.println("迭代方法生成的");
		root = BinaryTree.createByCompletePre(preOrderComplete);
		traversalTreePrintln(root);
		
		//二叉树的节点数
		System.out.println("二叉树的节点数========================");
		System.out.println("递归方法");
		System.out.println(BinaryTree.getSizeRecursion(root));
		System.out.println("迭代方法");
		System.out.println(BinaryTree.getSize(root));
		
		//二叉树的深度或者高度或者层数
		System.out.println("二叉树的深度========================");
		System.out.println("递归方法");
		System.out.println(BinaryTree.getHeightRecursion(root));
		System.out.println("迭代方法");
		System.out.println(BinaryTree.getHeight(root));
		
		//二叉树的宽度(最多的一层的节点数)
		System.out.println("二叉树的宽度========================");
		System.out.println("递归方法");
		System.out.println(BinaryTree.getMaxWidthRecursion(root));
		System.out.println("迭代方法");
		System.out.println(BinaryTree.getMaxWidth(root));
	}
	
	public static List<String> createPreOrder(){
		List<String> preOrder = new ArrayList<>();
		preOrder.add("1");
		preOrder.add("2");
		preOrder.add("4");
		preOrder.add("5");
		preOrder.add("3");
		preOrder.add("6");
		return preOrder;
	}
	
	public static List<String> createInOrder(){
		List<String> preOrder = new ArrayList<>();
		preOrder.add("4");
		preOrder.add("2");
		preOrder.add("5");
		preOrder.add("1");
		preOrder.add("3");
		preOrder.add("6");
		return preOrder;
	}
	
	public static void traversalTreePrintln(BinaryTreeNode<String> root) {
		System.out.println("二叉树的遍历");
		System.out.println("前序遍历");
		System.out.println("递归方法：" + BinaryTree.traversalPreOrderRecursion(root));
		System.out.println("迭代方法：" + BinaryTree.traversalPreOrder(root));
		System.out.println("中序遍历");
		System.out.println("递归方法：" + BinaryTree.traversalInOrderRecursion(root));
		System.out.println("迭代方法：" + BinaryTree.traversalInOrder(root));
		System.out.println("后序遍历");
		System.out.println("递归方法：" + BinaryTree.traversalPostOrderRecursion(root));
		System.out.println("迭代方法：" + BinaryTree.traversalPostOrder(root));
		System.out.println("层序遍历");
		System.out.println("递归方法：" + BinaryTree.traversalLevelOrderRecursion(root));
		System.out.println("迭代方法：" + BinaryTree.traversalLevelOrder(root));
	}
}
