package com.lisijietech.algorithm.module.binarytree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * 二叉树操作<br>
 * 二叉树相关：<br>
 * https://www.jianshu.com/p/bf73c8d50dc2<br>
 * http://c.biancheng.net/view/3385.html<br>
 * https://blog.csdn.net/viafcccy/article/details/91312699<br>
 * https://blog.csdn.net/weixin_42067873/article/details/104491892<br>
 * https://www.cnblogs.com/muyishuo/p/11238247.html<br>
 * 二叉树实现相关：
 * https://blog.csdn.net/qq_34120430/article/details/80043472，可能实现方法不对可以参考思路和规范<br>
 * 前序中序后序层序遍历相关：<br>
 * https://blog.csdn.net/loading___JJ/article/details/102731378，有前中序生成二叉树数据相关方法<br>
 * https://www.cnblogs.com/xbjhs/p/12108230.html<br>
 * https://blog.csdn.net/cedarjo/article/details/88982277，java对象的二叉树链式结构和顺序结构（感觉就是层序列表的二叉树操作）<br>
 * https://www.cnblogs.com/bigsai/p/11393609.html<br>
 * https://baike.baidu.com/item/%E4%BA%8C%E5%8F%89%E6%A0%91%E9%81%8D%E5%8E%86/9796049<br>
 * @author lisijie
 * @param <T>
 * @date 2020-8-8
 */
public class BinaryTree<T> {
	/**
	 * 二叉树根节点
	 */
	private BinaryTreeNode<T> node;
	
	/**
	 * 前序中序，或者后序中序，或者层序中序，能确定一个唯一的二叉树。
	 * 当然，二叉树可以根据上述规则生成确定的二叉树。
	 * 
	 * 但更简单的是通过顺序存储结构的二叉树，来生成链式结构二叉树。
	 * 顺序存储结构，空节点用数据用null填充。
	 * 顺序存储一般是按照层序把数据存储在数组中。使二叉树转换成一个完全二叉树（满二叉树也是完全，但可能会用更多存储空间）。
	 * 顺序存储也可以按照前序中序后序存储在数组中，当然也需要填充转换，不一定转换为完全（或者满）二叉树，可以简化或冗余。
	 */
	public BinaryTree() {}
	
	/**
	 * 前序，中序列表生成二叉树。
	 * 
	 * @param preOrder 前序
	 * @param inOrder 中序
	 */
	public BinaryTree(List<T> preOrder,List<T> inOrder) {
		long start = System.currentTimeMillis();
		
		//递归思路生成二叉树。
		this.node = createByPreInRecursion(preOrder, inOrder);
		
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) + "ms");
	}
	
	public BinaryTreeNode<T> getNode() {
		return node;
	}
	
	public void setNode(BinaryTreeNode<T> node) {
		this.node = node;
	}
	
	/**
	 * 生成链式存储结构二叉树，通过前序和中序列表。
	 * 递归方式。
	 * 前序中序，或者后序中序，或者层序中序，能确定一个唯一的二叉树。
	 * https://www.cnblogs.com/dddyyy/p/10682890.html
	 * @param <E>
	 * @param preOrder 前序
	 * @param inOrder 中序
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByPreInRecursion(List<E> preOrder,List<E> inOrder){
		//树的节点总数
		int len = preOrder.size();
		//根节点数据
		E rData = preOrder.get(0);
		
		//生成根节点，并设置根节点数据。
		BinaryTreeNode<E> root = new BinaryTreeNode<>();
		root.setData(rData);
		//树节点只有一个时，表示叶子结点，没有左右子节点了，结束递归。
		if(len == 1) {
			root.setLeft(null);
			root.setRight(null);
			return root;
		}
		
		//得到中序列表的根节点索引值。
		//要注意参数化类型的实际类型对象的equals方法，需要重写，或者优化方法参数传入比较器。
		int rIndex = 0;
		for(int i = 0;i < len;i++) {
			if(rData.equals(inOrder.get(i))) {
				rIndex = i;
				break;
			}
		}
		
		//递归设置根节点左孩子节点和右孩子节点。
		//左右孩子节点相当于根节点的左右子树的根节点。
		//所以递归调用方法，传入左子树或者右子树的前序中序列表，就能返回子树的根节点。
		
		//中序根节点数据索引如果大于0，代表根节点有左子树。否则根节点只有右子树。
		if(rIndex > 0) {
			//左子树前序
			List<E> leftPre = new ArrayList<>();
			//左子树中序
			List<E> leftIn = new ArrayList<>();
			for(int j = 0;j < rIndex;j++) {
				//rIndex是中序的根数据索引，小于索引的是左子树，大于索引的是右子树。
				//前序第一个是根节点数据,左子树前序从下标1开始。
				leftPre.add(preOrder.get(j + 1));
				leftIn.add(inOrder.get(j));
			}
			//左子树递归
			root.setLeft(createByPreInRecursion(leftPre, leftIn));
		}else {
			root.setLeft(null);
		}
		
		//中序根节点数据索引如果小于中序列表len - 1，代表根节点有右子树。否则根节点只有左子树。
		if(len - 1 - rIndex > 0) {
			//右子树前序
			List<E> rightPre = new ArrayList<>();
			//右子树中序
			List<E> rightIn = new ArrayList<>();
			for(int j = rIndex + 1;j < len;j++) {
				//右子树前序和中序不包括根节点数据。所以起始下标为j = rIndex + 1;
				rightPre.add(preOrder.get(j));
				rightIn.add(inOrder.get(j));
			}
			//右子树递归
			root.setRight(createByPreInRecursion(rightPre, rightIn));
		}else {
			root.setRight(null);
		}
		
		return root;
	}
	
	/**
	 * 生成链式存储结构二叉树，通过完全二叉树层序列表（即，顺序存储结构的容易操作的一种二叉树）。
	 * 递归方式。
	 * 思路和非递归方式一样，通过左右子节点在列表的索引规律来实现。
	 * @param <E>
	 * @param completeLevelTree 顺序存储结构的二叉树，完全二叉树层序列表。注意，节点数据data为null，代表这个节点是填充节点。
	 * @param i 节点索引
	 *  二叉树：
	 *        a
	 *    b        c
	 *      d        e
	 *       f
	 *  顺序存储结构层序列表：[a,b,c,nl,d,nl,e,nl,nl,nl,f]
	 * 	              a
	 * 	      b               c
	 * 	  nl      d       nl      e
	 *  nl  nl  nl  f
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByCompleteLevelRecursion(List<E> completeLevelTree,int i){
		if(i >= completeLevelTree.size()) {
			return null;
		}
		if(completeLevelTree.get(i) == null) {
			return null;
		}
		BinaryTreeNode<E> node = new BinaryTreeNode<>(completeLevelTree.get(i));
		node.setLeft(createByCompleteLevelRecursion(completeLevelTree,2 * i + 1));
		node.setRight(createByCompleteLevelRecursion(completeLevelTree,2 * i + 2));
		return node;
	}
	
	/**
	 * 生成链式存储结构二叉树，通过完全二叉树层序列表。
	 * 包装方法。
	 * @param <E>
	 * @param completeLevelTree
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByCompleteLevelRecursion(List<E> completeLevelTree){
		return createByCompleteLevelRecursion(completeLevelTree,0);
	}
	
	/**
	 * 生成链式存储结构二叉树，通过完全二叉树层序列表。
	 * 非递归方式。
	 * 顺序存储结构层序列表的规则，是填充成了完全二叉树，按照层数从小到大，每层按从左到右，这样的顺序存储的。
	 * 思路1：
	 * 计算当前层容量，按层生成节点，并将当前层节点按顺序设置父层节点左右子属性，然后当前层节点按顺序生成新的父层队列，进行新的循环。
	 * 思路2，优化，采用：
	 * 根节点以index = 1开始，那某节点的索引为index = i，左子节点是index = 2 * i，右子节点index = 2 * i + 1，父节点index = i/2。
	 * 一般索引是从0开始，则某节点index = i，左子节点index = 2 * i + 1，右子节点index = 2 * i + 2，父节点index = (i - 1)/2。
	 * 这样如果根节点索引从1开始，最后一个元素index = i，那只需要遍历到i/2节点，其子节点的索引都是可以计算确定的。
	 * 那根节点索引为0开始，则只需要遍历到(i + 1)/2 - 1
	 * <br>
	 * 参考：
	 * https://blog.csdn.net/android_heng/article/details/76599302
	 * @param <E>
	 * @param completeLevelTree 顺序存储结构的二叉树，完全二叉树层序列表
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByCompleteLevel(List<E> completeLevelTree){
		//校验参数，在上层业务中校验比较麻烦，校验规则复杂。这里先做简单空校验。
		//层序第一个元素是null，代表没有根节点，是空树，直接返回null。
		if(completeLevelTree == null || completeLevelTree.size() == 0 || completeLevelTree.get(0) == null) {
			return null;
		}
		
		//直接把层序存储列表元素全部转换为节点，只需要遍历设置他们的左右节点属性关系就行。
		//这种思路比较直接，但存储空间使用稍大，多了一次树的遍历，但可以优化，用队列或栈的存储方式减小存储空间。
		//当然优化时，遍历实现也需要一定改变。
		List<BinaryTreeNode<E>> nodes = new ArrayList<>();
		for(E element : completeLevelTree) {
			nodes.add(new BinaryTreeNode<E>(element));
		}
		
		//原来：len除以2取商运算，相当于最后一个索引为i，(i + 1)/2等于(i - 1)/2 + 1
		//优化：len 除以 2 - 1 是为了单独处理最后一个节点的父节点设置和判断是否有右子节点，防止数组越界。
		int len = completeLevelTree.size();
		for(int i = 0;i < len / 2 - 1;i++) {
			//设置节点的左属性。
			nodes.get(i).setLeft(nodes.get(2 * i + 1));
			
			//这个if判断是因为，如果当前节点左节点就是最后一个节点，没有右节点，而且不会被null填充，设置右节点会边界超出异常。
			//所以可以优化，没理由每次遍历都判断，只存在于最后节点的判断。
			//优化，只遍历到最后一个节点的父节点的前一个节点，即(i - 1)/2的前一个节点。最后一个节点的父节点放在循环外处理。
			//设置节点的右属性。
//			if(2 * i + 2 < len) {
//				nodes.get(i).setRight(nodes.get(2 * i + 2));
//			}
			nodes.get(i).setRight(nodes.get(2 * i + 2));
		}
		//单独处理最后一个节点的父节点，达到优化速度。
		int lastNodeparent = len / 2 - 1;
		nodes.get(lastNodeparent).setLeft(nodes.get(2 * lastNodeparent + 1));
		if(2 * lastNodeparent + 2 < len) {
			//如果有右子节点，设置右节点属性。
			nodes.get(lastNodeparent).setRight(nodes.get(2 * lastNodeparent + 2));
		}
		
		//冗余写法是为了清楚思路，根节点就是第一个节点。遍历完成节点的左右属性设置，二叉树的关系就设置完成了，直接返回根节点。
		BinaryTreeNode<E> root = nodes.get(0);
		return root;
	}
	
	/**
	 * 生成链式存储结构二叉树，通过顺序存储结构二叉树前序列表。
	 * 递归方式。
	 * <br>
	 * 实现思路参考下列网站，但是做了些修改优化，达到静态方法直接使用，不耦合实例对象或者静态成员变量的效果。
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * https://www.cnblogs.com/ldxsuanfa/p/10951028.html<br>
	 * @param <E>
	 * @param completePreTree 顺序存储结构的二叉树前序列表。
	 * 注意，节点数据data为null，代表这个节点是填充节点。
	 * 节点没有子节点了或者只有一个子节点时，需要用null节点填充没有的子节点，填充的null值的节点即使不是叶子节点，也没必要再填充子节点了。
	 * 这样就达到判断切换下一个节点是左节点还是右节点。
	 *  所以前序的填充和层序不同，不必填充为完全二叉树。
	 *  二叉树：
	 *     a
	 *   b   e
	 *  c d   f	
	 *  顺序存储结构前序列表：[a,b,c,nl,nl,d,nl,nl,e,nl,f,nl,nl]
	 * 	           a
	 * 	     b             e
	 * 	 c       d     nl      f
	 * nl nl   nl nl         nl nl
	 * @param node 当前节点
	 * @param index 当前节点索引，用了int[]数组包装，主要是解耦静态成员变量，防止出现问题。当然也可以用参考网址实现思路，递归索引。
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByCompletePreRecursion(List<E> completePreTree,int[] index){
		//当前节点下标
		int i = index[0];
		
		//如果当前节点下标超过二叉树列表，就结束递归。
		if(i >= completePreTree.size()) {
			return null;
		}
		
		//如果当前节点数据是null，代表是二叉树前序列表的填充节点，其如果有孩子节点，也都是填充节点，所以结束递归。
		E data = completePreTree.get(i);
		if(data == null) {
			return null;
		}
		//创建节点对象并赋值二叉树对应的数据值
		BinaryTreeNode<E> node = new BinaryTreeNode<>(data);
		
		//左右节点递归赋值
		++index[0];//索引加1，设置下一个节点（左节点）。相当于index[0] = i + 1;
		BinaryTreeNode<E> left = createByCompletePreRecursion(completePreTree,index);
		node.setLeft(left);
		++index[0];//左子节点完成后，索引加1，设置下一个节点（右节点）。
		BinaryTreeNode<E> right = createByCompletePreRecursion(completePreTree,index);
		node.setRight(right);
		
		return node;
	}
	
	/**
	 * 
	 * 生成链式存储结构二叉树，通过顺序存储结构二叉树前序列表。
	 * 递归方式。
	 * 包装方法。主要包装节点当前索引。
	 * @param <E>
	 * @param completePreTree 顺序存储结构的二叉树前序列表。
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByCompletePreRecursion(List<E> completePreTree){
		int[] i = new int[]{0};
		return createByCompletePreRecursion(completePreTree,i);
	}
	
	/**
	 * 生成链式存储结构二叉树，通过顺序存储结构二叉树前序列表。
	 * 非递归方法。
	 * 原理同递归方法一样，只是实现思路略有不同。主要通过容器记录父节点队列，并且判断左右子节点填充切换。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472，其用到java的容器集合Stack类，先进后出<br>
	 * @param <E>
	 * @param completePreTree
	 * @return
	 */
	public static <E> BinaryTreeNode<E> createByCompletePre(List<E> completePreTree){
		//校验参数，在上层业务中校验比较麻烦，校验规则复杂。这里先做简单空校验。
		//前序第一个元素是null，代表没有根节点，是空树，直接返回null。
		if(completePreTree == null || completePreTree.size() == 0 || completePreTree.get(0) == null) {
			return null;
		}
		//根节点
		BinaryTreeNode<E> root = new BinaryTreeNode<>();
		//父节点队列，父节点索引（-1代表没有父节点），可以改用Stack类替换。
		List<BinaryTreeNode<E>> queue = new ArrayList<>();
		int parentIndex = -1;
		//当前节点,以及当前节点在顺序存储结构前序列表中的索引
		BinaryTreeNode<E> node = root;
		int index = 0;
		//顺序结构前序列表大小
		int len = completePreTree.size();
		while(index < len) {
			E element = completePreTree.get(index);
			if(element != null) {
				//有数据，则设置当前节点数据，以及当前节点的父节点的左节点属性。
				
				//赋值到当前节点
				node.setData(element);
				//如果有父节点，当前节点有数据则赋值到其父节点的左节点属性。数据为null值时，节点属性不能为空节点，而是null。
				//其实这个if判断可以优化掉，这只是因为根节点没有父节点而产生的。当前节点起始从根节点的左子节点开始就行。
				//这需要在循环外校验一次根节点，校验通过并初始化。
				//这些问题都是我用ArrayList代替Stack或者Queue产生的。
				//但本质和效果是一样的，因为我认为Stack元素弹出操作应该和删除元素一样或者是索引减1。
				if(parentIndex > -1) {
					queue.get(parentIndex).setLeft(node);
				}
				//当前节点加入父节点队列。因用ArrayList，没用Stack，Queue，实现细节有些差异。
				//父节点索引加1
				++parentIndex;
				//父节点下标没有超过queue的容量大小就更新元素，超过了就添加元素
				if(parentIndex < queue.size()) {
					queue.set(parentIndex, node);
				}else {
					queue.add(node);
				}
				//之后的代码if else中都共有，抽离出去。
			}else {
				//如果当前节点数据为空，则需要判断当前节点是父节点的左子节点还是右子节点。
				//判断父节点的左属性是null，则当前节点一定是左子节点。
				//索引加1，当前节点变为右子节点，判断是否是null。
				//不是null,则创建右子节点和设置父节点右属性，当前节点设为新的父节点添加进父节点队列，新的父节点索引加1。
				//如果是null，直接进行下面一步的操作。
				//索引加1，当前节点设置为新的空节点，进行下一轮循环。
				//注意，这中方法if语句中也要通过父节点判断新节点是左节点还是右节点。父节点左属性为null就是左节点了，否则是右节点。
				//优化方式2，采用：
				//通过前序规则发现，填充空节点第一次出现一定出现在左节点的子节点中，并且空节点的下一个节点一定是右节点，
				//所以按照左右右右的规则，先判断左子节点，然后循环判断右子节点，直到不是空节点为止。
				//就可以确定不为空的节点下一个索引一定是左子节点。
				//或者索引最后一个元素，就结束循环了。
				
				//当前节点（左节点）数据为空，设置父节点的左属性点为空。
				//这步没必要了，在if语句块中做了改进，父节点在有数据时左属性不会被预先设为空节点了。
//				BinaryTreeNode<E> parentNode = queue.get(parentIndex);
//				parentNode.setLeft(null);
				
				//左节点数据为空了，索引加1，判断右节点。父节点不变。
				++index;
				if(index >= len) {
					//数据不规范，尾添加了null填充，跳出循环，返回二叉树。或者报错。
					System.out.println("顺序存储二叉树前序列表数据不规范，结尾用了一个null元素填充，比较冗余");
					break;
				}
				E rightData = completePreTree.get(index);
				//循环判断右子节点是否为空，直到不为空为止，或者二叉树范围结束了。
				//其实不需要判断二叉树范围的，但是防止顺序存储结构冗余，在末尾添加了null填充。
				while(rightData == null && index < len - 1) {
					//如果为空，设置右子节点为null。这步可省略，因为新建节点对象成员属性并未初始化，所以就是null；
					//parentNode.setRight(null);
					//父节点的索引往前移动减1，到当前父节点的父节点。
					--parentIndex;
					//当前节点（右节点）索引加1，获得移动索引后的父节点的右子节点。
					++index;
					rightData = completePreTree.get(index);
				}
				//右子节点不为空，生成节点，然后赋值给父节点的右子节点属性。
				//本不需要null判断，是防止顺序存储结构冗余，在末尾添加了null填充。
				BinaryTreeNode<E> rightNode = null;
				if(rightData != null) {
					rightNode = new BinaryTreeNode<>(rightData);
					BinaryTreeNode<E> parentNode = queue.get(parentIndex);
					parentNode.setRight(rightNode);
				}else {
					//数据不规范，尾添加了null填充，跳出循环，返回二叉树。或者报错。
					System.out.println("顺序存储二叉树前序列表数据不规范，结尾用了多个个null元素填充，比较冗余");
					break;
				}
				//当前节点就是右子节点。
				node = rightNode;
				//当前节点加入父节点队列，单是与左节点不同，右节点是覆盖其父节点，
				//因为不会再访问之前的节点了，而且右节点结束时，下一个节点是右节点的父节点的父节点。
				queue.set(parentIndex, node);
				//之后的代码if else中都共有，抽离出去。
			}
			
			//生成新的左节点，索引后移+1。新的节点进行下一轮null值判断。
			node = new BinaryTreeNode<>();
			++index;
		}
		return root;
	}
	
	/**
	 * 二叉树链式存储结构转化为顺序存储结构，前序存储。
	 * 前序顺序存储不一定会填充为完全二叉树。
	 * 可以直接使用前序遍历traversalPreOrder(BinaryTreeNode<E> node)，在空节点时，左右子树切换时，添加null节点填充。
	 * 但是结束会用null节点填充，比较冗余，需要优化处理。
	 * 如果在循环外，判断列表最后的所有连续null元素进行截取复制列表，效率可能高也肯低一些（截取效率大概率低），取决于列表截取的方法效率。
	 * 如果算法循环内判断结束边界，则只需要提前判断下一次循环stack内有没有元素，有则需要填充。没有，则不需要填充，后序填充都是冗余的；
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> convertToCompletePre(BinaryTreeNode<E> node){
		if(node == null) {
			return null;
		}
		List<E> list = new ArrayList<>();
		Stack<BinaryTreeNode<E>> stack = new Stack<>();
		stack.push(node);
		while(!stack.isEmpty()) {
			BinaryTreeNode<E> currentNode = stack.pop();
			
			//先序顺序null填充。
			if(currentNode == null) {
				list.add(null);
				continue;
			}
			
			list.add(currentNode.getData());
			
			//注意，是先push右节点，后push左节点。这样每次循环左节点都在栈顶部，先遍历根左节点，右节点会形成队列。
			//这样能巧妙的利用Stack
			if(currentNode.getRight() != null) {
				stack.push(currentNode.getRight());
			}else {
				//顺序存储结构要用null节点填充，区分左右节点。
				//个人认为，这样写在else里判断，可以达到短路调用效果，是null才执行代码，逻辑上效率高一点，是完全二叉树那效率很高。
				//但实际效率，要了解if else的底层原理，甚至是物理硬件原理才能知晓。以后再深入学习。
				
				//判断stack是否为空，是因为判断二叉树结尾时冗余的null节点填充。
				if(!stack.isEmpty()) {
					//先序遍历的右节点需要先压入栈，形成队列。
					stack.push(null);
				}
			}
			
			if(currentNode.getLeft() != null) {
				stack.push(currentNode.getLeft());
			}else {
				//判断stack是否为空，是因为判断二叉树结尾时冗余的null节点填充。
				if(!stack.isEmpty()) {
					//先序遍历的左节点后压入栈，下次循环先进行遍历。
					//因为这里是null值填充，没有左右子节点，而且确定下次循环必定执行，所以无需等到下次循环遍历，可以直接填充进列表中。
					//stack.push(null);
					list.add(null);
				}
			}
		}
		return list;
	}
	
	/**
	 * 二叉树链式存储结构转化为顺序存储结构，层序存储。
	 * 层序存储会转换为完全二叉树。但不必转化为满二叉树。
	 * 使用traversalLevelOrder层序遍历，在空节点时，左右子树切换时，必须添加null节点填充，转换成完全二叉树。
	 * 但是直接使用层序遍历方法会在末尾添加冗余的null节点，甚至添加一倍节点数量的填充null节点。
	 * 所以需要优化。需要知道二叉树的结束边界。
	 * 方法一，预先遍历得到结束边界，如二叉树深度或元素个数等。方法二，在遍历中得知元素结束边间，采用。或者其他什么方法，待思考，学习。
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> convertToCompleteLevel(BinaryTreeNode<E> node){
		if(node == null) {
			return null;
		}
		List<E> list = new ArrayList<>();
		int preNum = 1;//预先获得的非填充节点个数，为0则代表没有节点了，树遍历结束。
		Queue<BinaryTreeNode<E>> queue = new LinkedList<>();
		queue.offer(node);
		while(preNum > 0) {
			node = queue.poll();
			//填充
			if(node == null) {
				list.add(null);
				continue;
			}
			//非填充节点的遍历，预先数量减1
			--preNum;
			list.add(node.getData());
			
			if(node.getLeft() != null) {
				queue.offer(node.getLeft());
				//预先数量加1
				++preNum;
			}else {
				queue.offer(null);
			}
			if(node.getRight() != null) {
				queue.offer(node.getRight());
				//预先数量加1
				++preNum;
			}else {
				queue.offer(null);
			}
		}
		return list;
	}
	
	/**
	 * 前序遍历二叉树，返回按前序顺序的列表。
	 * 递归方式。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * https://blog.csdn.net/android_heng/article/details/76599302<br>
	 * @param <E>
	 * @param node
	 * @param list
	 * @return
	 */
	public static <E> List<E> traversalPreOrderRecursion(BinaryTreeNode<E> node,List<E> list){
		if(node == null) {
			return null;
		}
		
		list.add(node.getData());
		
		traversalPreOrderRecursion(node.getLeft(),list);
		traversalPreOrderRecursion(node.getRight(),list);
		
		return list;
	}
	
	/**
	 *  前序遍历二叉树，返回按前序顺序的列表。
	 * 递归方式。
	 * 包装方法。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalPreOrderRecursion(BinaryTreeNode<E> node){
		return traversalPreOrderRecursion(node,new ArrayList<>());
	}
	
	
	/**
	 * 中序遍历二叉树，返回按中序顺序的列表。
	 * 递归方式。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * https://blog.csdn.net/android_heng/article/details/76599302<br>
	 * @param <E>
	 * @param node
	 * @param list
	 * @return
	 */
	public static <E> List<E> traversalInOrderRecursion(BinaryTreeNode<E> node,List<E> list){
		if(node == null) {
			return null;
		}
		
		traversalInOrderRecursion(node.getLeft(),list);
		list.add(node.getData());
		traversalInOrderRecursion(node.getRight(),list);
		
		return list;
	}
	
	/**
	 * 中序遍历二叉树，返回按中序顺序的列表。
	 * 递归方式。
	 * 包装方法。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalInOrderRecursion(BinaryTreeNode<E> node){
		return traversalInOrderRecursion(node,new ArrayList<>());
	}
	
	/**
	 * 后序遍历二叉树，返回按后序顺序的列表。
	 * 递归方式
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * https://blog.csdn.net/android_heng/article/details/76599302<br>
	 * @param <E>
	 * @param node
	 * @param list
	 * @return
	 */
	public static <E> List<E> traversalPostOrderRecursion(BinaryTreeNode<E> node,List<E> list){
		if(node == null) {
			return null;
		}
		
		traversalPostOrderRecursion(node.getLeft(),list);
		traversalPostOrderRecursion(node.getRight(),list);
		list.add(node.getData());
		
		return list;
	}
	
	/**
	 * 后序遍历二叉树，返回按后序顺序的列表。
	 * 递归方式。
	 * 包装方法。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalPostOrderRecursion(BinaryTreeNode<E> node){
		return traversalPostOrderRecursion(node,new ArrayList<>());
	}
	
	/**
	 * 层序遍历二叉树，返回按层序顺序的列表。
	 * 递归方式。
	 * 层序遍历的递归方式效率不高，遍历当前层，预先存储下一层的列表，把下一层传入方法进行递归。
	 * 可参考获取树高非递归方法{@link #getHeight(BinaryTreeNode)}的思路。
	 * 其他参考：
	 * https://www.cnblogs.com/liuyang0/p/6271331.html<br>
	 * @param <E>
	 * @param levels 二叉树当前层节点列表。
	 * @return
	 */
	public static <E> List<E> traversalLevelOrderRecursion(List<BinaryTreeNode<E>> levels){
		if(levels.isEmpty()) {
			return null;
		}
		List<E> elements =  new ArrayList<>();
		List<BinaryTreeNode<E>> nextLevels =  new ArrayList<>();
		for(BinaryTreeNode<E> node : levels) {
			elements.add(node.getData());
			
			if(node.getLeft() != null) {
				nextLevels.add(node.getLeft());
			}
			if(node.getRight() != null) {
				nextLevels.add(node.getRight());
			}
		}
		
		List<E> nextElemts = traversalLevelOrderRecursion(nextLevels);
		if(nextElemts != null) {
			elements.addAll(nextElemts);
		}
		
		return elements;
	}
	
	/**
	 * 层序遍历二叉树，返回按层序顺序的列表。
	 * 递归方式。
	 * 包装方法。
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalLevelOrderRecursion(BinaryTreeNode<E> node){
		if(node == null) {
			return null;
		}
		List<BinaryTreeNode<E>> list = new ArrayList<>();
		list.add(node);
		return traversalLevelOrderRecursion(list);
	}
	
	/**
	 *  前序遍历二叉树，返回按前序顺序的列表。
	 * 非递归方式。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/cedarjo/article/details/88982277<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalPreOrder(BinaryTreeNode<E> node){
//		if(node == null) {
//			return null;
//		}
//		List<E> list = new ArrayList<>();
//		Stack<BinaryTreeNode<E>> stack = new Stack<>();
//		BinaryTreeNode<E> tempNode = node;
//		while(true) {
//			
//			while(tempNode != null) {
//				list.add(tempNode.getData());
//				stack.push(tempNode);
//				tempNode = tempNode.getLeft();
//			}
//			
//			//这个判断可以放在外层while循环判断中，但是需要设置初始栈值，而且之后的代码需要修改做冗余判断。
//			//优化后就成了这个写法
//			if(stack.isEmpty()) {
//				break;
//			}
//			
//			tempNode = stack.pop();
//			tempNode = tempNode.getRight();
//		}
//		return list;
		
		//更巧妙的利用Stack的写法。
		
		if(node == null) {
			return null;
		}
		List<E> list = new ArrayList<>();
		Stack<BinaryTreeNode<E>> stack = new Stack<>();
		stack.push(node);
		while(!stack.isEmpty()) {
			BinaryTreeNode<E> currentNode = stack.pop();
			
			list.add(currentNode.getData());
			
			//注意，是先push右节点。这样能巧妙的利用Stack。
			if(currentNode.getRight() != null) {
				stack.push(currentNode.getRight());
			}
			if(currentNode.getLeft() != null) {
				stack.push(currentNode.getLeft());
			}
		}
		return list;
	}
	
	/**
	 * 中序遍历二叉树，返回按中序顺序的列表。
	 * 非递归方式。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * https://blog.csdn.net/cedarjo/article/details/88982277<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalInOrder(BinaryTreeNode<E> node){
		if(node == null) {
			return null;
		}
		List<E> list = new ArrayList<>();
		Stack<BinaryTreeNode<E>> stack = new Stack<>();
		BinaryTreeNode<E> tempNode = node;
		while(true) {
			
			while(tempNode != null) {
				stack.push(tempNode);
				tempNode = tempNode.getLeft();
			}
			
			//这个判断可以放在外层while循环判断中，但是需要设置初始栈值，而且之后的代码需要修改做冗余判断。
			//优化后就成了这个写法
			if(stack.isEmpty()) {
				break;
			}
			
			tempNode = stack.pop();
			list.add(tempNode.getData());
			tempNode = tempNode.getRight();
		}
		return list;
	}
	
	/**
	 * 后序遍历二叉树，返回按后序顺序的列表。
	 * 非递归方式。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/cedarjo/article/details/88982277，多种实现，基本写法到等价优化写法过程。<br>
	 * https://www.cnblogs.com/bigsai/p/11393609.html，思路讲解，多种实现，优化写法<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472，思路一样的，节点增加冗余属性耦合，不推荐<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalPostOrder(BinaryTreeNode<E> node){
		List<E> list = new ArrayList<>();
		//类似于父节点队列，栈。
		Stack<BinaryTreeNode<E>> stack = new Stack<>();
		//节点是否遍历过右子节点的标识。也可以换成HashMap，记录遍历次数，第二次后就可以输出节点了，实现代码原理一样，稍作修改。
		Set<BinaryTreeNode<E>> rightFlags = new HashSet<>();
		//语句 !stack.isEmpty() 要放前面，因为空栈在第一次循环时、结尾循环时出现，需要判断node。|| 是逻辑短路的，其他时候不用判断node。
		while(!stack.isEmpty() || node != null) {
			//这里if本来是while的node != null循环判断，else内的代码需要!stack.isEmpty()判断，但通过最外层的while判断的等价优化，
			//形成如下代码写法。
			if(node != null) {
				stack.push(node);
				node = node.getLeft();
			}else {
				BinaryTreeNode<E> parent = stack.peek();
				if(rightFlags.contains(parent)) {
					//如果判断过右子节点为null，就输出当前父节点，并弹出当前父节点不引用。
					//然后清除右子节点标识，可不清除，因为不会再次访问到当前节点了。
					//最后向上遍历父节点。由于写法优化，要在下个循环遍历。
					//但是向上的父节点左节点肯定是判断过了，所以栈弹出的节点只判断右节点。
					//此时，node节点应该赋值null，略过下个循环的左节点判断执行。
					//但是node节点还是上一个节点的右子节点null，可复用，就不用显示赋null值了。
					
					stack.pop();
					list.add(parent.getData());
					//清除右子节点标识。可不执行，因为不会再次访问到当前节点了。
					rightFlags.remove(parent);
				}else {
					//标识这个父节点遍历过左节点了。相当于第二次遍历过这个父节点了。
					rightFlags.add(parent);
					//当前节点（左子节点）赋值为其父节点的右子节点
					node = parent.getRight();
				}
			}
		}
		return list;
	}
	
	/**
	 * 层序遍历二叉树，返回按层序顺序的列表。
	 * 利用java队列Queue。
	 * 非递归方式。
	 * 参考：<br>
	 * https://www.cnblogs.com/bigsai/p/11393609.html<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472，写法简单，但效率有待优化<br>
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> List<E> traversalLevelOrder(BinaryTreeNode<E> node){
		if(node == null) {
			return null;
		}
		List<E> list = new ArrayList<>();
		Queue<BinaryTreeNode<E>> queue = new LinkedList<>();
		queue.offer(node);
		while(!queue.isEmpty()) {
			node = queue.poll();
			list.add(node.getData());
			if(node.getLeft() != null) {
				queue.offer(node.getLeft());
			}
			if(node.getRight() != null) {
				queue.offer(node.getRight());
			}
		}
		return list;
	}
	
	/**
	 * 二叉树节点数。
	 * 递归方法。
	 * 用遍历方法把打印换成节点数相加。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/lb812913059/article/details/83313657<br>
	 * @param node
	 * @return
	 */
	public static int getSizeRecursion(BinaryTreeNode<?> node) {
		if(node == null) {
			return 0;
		}
		int left = getSizeRecursion(node.getLeft());
		int right = getSizeRecursion(node.getRight());
		return 1 + left + right;
	}
	
	/**
	 * 二叉树节点数。
	 * 非递归方法。
	 * 用遍历方法把打印换成节点数相加。就选层序遍历实现。
	 * @param node
	 * @return
	 */
	public static int getSize(BinaryTreeNode<?> node) {
		if(node == null) {
			return 0;
		}
		int size = 0;
		Queue<BinaryTreeNode<?>> queue = new LinkedList<>();
		queue.offer(node);
		while(!queue.isEmpty()) {
			node = queue.poll();
			++size;
			if(node.getLeft() != null) {
				queue.offer(node.getLeft());
			}
			if(node.getRight() != null) {
				queue.offer(node.getRight());
			}
		}
		return size;
	}
	
	/**
	 * 二叉树高度或者深度或者总层数。
	 * 递归方法。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/lb812913059/article/details/83313657<br>
	 * https://blog.csdn.net/qq_34120430/article/details/80043472<br>
	 * @param node
	 * @return
	 */
	public static int getHeightRecursion(BinaryTreeNode<?> node) {
		if(node == null) {
			return 0;
		}
		int left = getHeightRecursion(node.getLeft());
		int right = getHeightRecursion(node.getRight());
		return left >= right ? left + 1 : right + 1 ;
	}
	
	/**
	 * 二叉树高度或者深度或者总层数。
	 * 非递归方法（迭代方法、循环方法）。
	 * <br>
	 * 参考：<br>
	 * https://www.cnblogs.com/gunduzi/p/12740903.html，此方法用了List,我用Queue，LinkedList实现，思路一样，实现稍有修改。
	 * 用List此方法占用存储空间多，可能效率稍高。Queue可能占用空间少，可能需要弹出或者删除元素操作，可能效率稍低。
	 * @param node
	 * @return
	 */
	public static int getHeight(BinaryTreeNode<?> node) {
		if(node == null) {
			return 0;
		}
		
		//当前高度（层数）
		int height = 0;
		//当前层节点数。
		int curSize = 0;
		//下一层节点数。
		int nextSize = 1;
		//当前节点在其所属层的下标
		int i = 0;
		
		Queue<BinaryTreeNode<?>> queue = new LinkedList<>();
		queue.offer(node);
		while(!queue.isEmpty()) {
			++height;
			curSize = nextSize;
			nextSize = 0;
			for(i = 0;i < curSize;i++) {
				node = queue.poll();
				if(node.getLeft() != null) {
					queue.offer(node.getLeft());
					++nextSize;
				}
				if(node.getRight() != null) {
					queue.offer(node.getRight());
					++nextSize;
				}
			}
		}
		return height;
	}
	
	/**
	 * 二叉树的宽度，指节点最多的一层的节点数。
	 * 递归方法。
	 * 参考高度方法{@link #getHeight(BinaryTreeNode)}的预先得到下层处理。
	 * 类似于层序遍历的递归方法，把层序输出修改为每层节点个数比较，返回最大个数。
	 * @param levels 当前层节点队列
	 * @param width 当前层节点个数
	 * @return
	 */
	public static int getMaxWidthRecursion(Queue<BinaryTreeNode<?>> levels) {
		if(levels.isEmpty()) {
			return 0;
		}
		
		//当前层元素个数
		int curSize = levels.size();
		
		for(int i = 0;i < curSize;i++) {
			BinaryTreeNode<?> node = levels.poll();
			if(node.getLeft() != null) {
				levels.add(node.getLeft());
			}
			if(node.getRight() != null) {
				levels.add(node.getRight());
			}
		}
		//下一层元素个数
		int nextSize = getMaxWidthRecursion(levels);
		
		return curSize >= nextSize ? curSize : nextSize;
	}
	
	/**
	 * 二叉树的宽度，指节点最多的一层的节点数。
	 * 递归方法。
	 * 包装方法。
	 * @param node
	 * @return
	 */
	public static int getMaxWidthRecursion(BinaryTreeNode<?> node) {
		if(node == null) {
			return 0;
		}
		Queue<BinaryTreeNode<?>> levels = new LinkedList<>();
		levels.offer(node);
		return getMaxWidthRecursion(levels);
	}
	
	/**
	 * 二叉树的宽度，指节点最多的一层的节点数。
	 * 非递归方法。
	 * 参考高度方法{@link #getHeight(BinaryTreeNode)}的预先得到下层处理。
	 * 类似于层序遍历的递归方法，把层序输出修改为每层节点个数比较，返回最大个数。
	 * <br>
	 * 参考：<br>
	 * https://blog.csdn.net/sinat_36246371/article/details/53445058
	 * @param node
	 * @return
	 */
	public static int getMaxWidth(BinaryTreeNode<?> node) {
		if(node == null) {
			return 0;
		}
		
		//最大宽度
		int maxSize = 0;
		//当前层节点数。
		int curSize = 0;
		//下一层节点数。
		int nextSize = 1;
		//当前节点在其所属层的下标
		int i = 0;
		
		Queue<BinaryTreeNode<?>> queue = new LinkedList<>();
		queue.offer(node);
		while(!queue.isEmpty()) {
			
			maxSize = curSize >= nextSize ? curSize : nextSize;
			
			curSize = nextSize;
			nextSize = 0;
			for(i = 0;i < curSize;i++) {
				node = queue.poll();
				if(node.getLeft() != null) {
					queue.offer(node.getLeft());
					++nextSize;
				}
				if(node.getRight() != null) {
					queue.offer(node.getRight());
					++nextSize;
				}
			}
		}
		return maxSize;
	}

}
