package com.lisijietech.algorithm.module.base.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 有两个大小为m和n的有序数组A和B，找出他们的中位数。
 * 暴力算法：
 * 可以参考牛客网，这里不自己实现了。
 * 两个数组排序，奇数长度数组，得到数组中间索引的值，偶数长度数组，得到中间两个索引的值，计算两个值的和除以2的double值。
 * 提升难度要求：
 *  时间复杂度在O(log(m + n))以内。
 *  问题转换成第k小的数。我认为是类似于二分查找，虽然是不那么直观，但根据条件可以找到算法规则。
 * @author lisijie
 * @date 2020-7-22
 */
public class MedianOfTwoSortedArrays {
	
	public static void main(String[] args) throws IOException {
		//算法网站要自己写输入输出。
		//如果是本地自己输入输出，是从控制台命令行输入，输出到控制台。
		//当然，System的.setxxx方法可以自定义设置输入输出流，不一定就是控制台输入流，可能是文件输入输出流，或者数据库等业务程序。
		//但是算法网站系统是如何对用户代码进行执行，并且输入输出的。
		//猜想：
		//算法网站可能自己实现代码编译器和加载器，执行器。执行后，结束运行线程，卸载销毁类。返回执行输出结果。
		//算法网站可能设置System的setxxx输入输出方法，为某种文件输入流和文件输出流，或者数据库等业务程序。
		//算法网站不太可能程序调用控制台，进行输入，获取输出，实现复杂而且没必要，机械交互还要走针对用户可视化程序多此一举。

		//控制台命令行输入。
		//测试用例[1,2,3,4,5],[1,2,3,4,5]或者[],[1]
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		//会无线循环，System.in输入流，包装后readLine会阻塞等待控制台输入，
		//控制台直接回车换行，截取掉回车换行结束符后，会读到""空字符串而不是null
		//所以要自己判断跳出，或者在方法中传入结束符。
		//在算法网站做题时，要无限循环，算法网站才能得到所有输入测试用例。
		while((str = br.readLine()) != null) {
			System.out.println("控制台读取到的命令行字符串：" + str + "，长度为" + str.length());
			
			String[] strArr = str.split("\\]");
			System.out.println("strArr数组：" + Arrays.toString(strArr) + "strArr数组个数：" + strArr.length);
			
			//去除数组首位残留的"["字符，判断字符串是否为空白，转换为int数组
			String aStr = strArr[0].substring(1);
			String[] aStrArr = aStr.length() == 0 ? new String[0] : aStr.split(",");
			System.out.println("aStr数组：" + Arrays.toString(aStrArr) + "个数：" + aStrArr.length);
			String bStr = strArr[1].substring(2);
			String[] bStrArr = bStr.length() == 0 ? new String[0] : bStr.split(",");
			System.out.println("bStr数组：" + Arrays.toString(bStrArr) + "个数：" + bStrArr.length);
			
			int[] a = new int[aStrArr.length];
			for(int i = 0; i < aStrArr.length;i++) {
				a[i] = Integer.parseInt(aStrArr[i]);
			}
			int[] b = new int[bStrArr.length];
			for(int i = 0; i < bStrArr.length;i++) {
				b[i] = Integer.parseInt(bStrArr[i]);
			}
			
			//两个有序数组中位数算法
			System.out.println(findMedianSortedArrays(a,b));
			//第k小的数
//			System.out.println(getKth(a, 0, b, 0, (a.length + b.length) / 2 + 1));
		}
	}
	
	/**
	 * 问题转换成第k小的数。
	 * 假设两个原序列升序排列，不影响算法时间复杂度。但是严谨的算法就要对数组是正序还是逆序做校验。
	 * 时间复杂度在O(log(m + n))以内。
	 * 递归方式。
	 * 借鉴牛客网的评论高赞C++和Java语言算法解析实现。
	 * @param A
	 * @param B
	 * @return 
	 */
	public static double findMedianSortedArrays(int[] A ,int[] B) {
		int m = A.length,n = B.length,total = m + n;
		//判断元素个数奇偶性
		if(total % 2 == 1) {
			//元素个数奇数个，中位数是最中间的元素值。
//			return getKthC(A, m, B, n, total / 2 + 1);//C++语言思路写法函数
			return getKth(A, 0, B, 0, total / 2 + 1);
		}else {
			//元素个数偶数个，中位数是中间两个元素值的平均数，2.0默认是float类型，为了向上转型为double
//			return (getKthC(A, m, B, n, total / 2) + getKthC(A, m, B, n, total / 2 + 1)) / 2.0;//C++语言思路写法函数
			return (getKth(A, 0, B, 0, total / 2) + getKth(A, 0, B, 0, total / 2 + 1)) / 2.0;
		}
	}
	
	/**
	 * 得到两个有序数组(升序)的第k小数。C++写法思路，学习用，java语言不建议使用。
	 * 时间复杂度在O(log(m + n))以内。
	 * 参考：<br>
	 * 牛客网leetcode专题MedianOfTwoSortedArrays题目的评论高赞C++写法思路<br>
	 * 求两个排序数组（升序）中的第k小的数 https://blog.csdn.net/chaiqunxing51/article/details/51143019
	 * @param A 数组。
	 * @param m A数组的查找范围长度。
	 * 由于参考C++写法，实际上这里就是数组的length。java中可以不需要m,n两个参数。
	 * 而且递归时，C++的语法截取了(个人理解)A或B数组的元素，再设置数组相应的长度m,n。
	 * java中截取数组需要根据起始下标截取，遍历重新生成新的有效查询范围元素的数组，算法耗时增加。
	 * java语言写法，没必要去截取数组，记录起始下标，m,n换成aStart,bStart，算法做相应修改优化，就能得到适合java语言的算法。
	 * @param B 数组
	 * @param n B数组的查找范围长度。
	 * @param k 第k小的数
	 * @return
	 */
	public static double getKthC(int[] A,int m,int[] B,int n,int k) {
		//保持元素较少的数组位于前面位置
		if(m > n) {
			return getKthC(B, n, A, m, k);
		}
		//如果前面位置的数组为空，则直接返回后面位置数组索引为k-1的元素值。
		if(m == 0) {
			return B[k-1];
		}
		//m,n参数边界。
		//上面两个if语句主要是判断长度小的数组是否还有有效范围，如果没有则直接返回另一个数组索引为k-1的数。
		//上面两条if可以替换成如下语句。边界条件。
		//if(m > n) return getKthC(B, n, A, m, k);此判断和pa，pb值也有关系，没有可能会出现B[pb-1]下标越界异常
		//if(m == 0) return B[k-1];
		//if(n == 0) return A[k-1];这个判断是多余的，可不写，因为参数已经校验排序过了，保证了m < n。
		
		//k参数边界。
		//如果k等于1，返回两个数组头元素的最小值。
		//逻辑上k=1，就是第一小的数。两个有序数组比较头元素小的就是最终结果。k也不能小于1，没意义。
		//程序算法中，不判断k==1的话，后序程序获取数组元素索引会小于0报错，或者索引会保持在最终结果值和后一个值，没有结束边界循环递归。
		//因为假设了数组升序，所以k=1时，两个值较小的那个就是最终值。
		if(k == 1) {
			return Math.min(A[0], B[0]);
		}
		//第k小的数，两个数组取第pa，pb小的元素来比较，且pa + pb = k，第k小的数一定在所有元素有序集合的pa元素和pb元素之间。
		//可以用二分法来查找。
		//这里必须要保证m < n，否则很大可能n < k/2，即n < pb，导致B[pb-1]数组下标越界异常。
		int pa = Math.min(k/2, m), pb = k - pa;
		if(A[pa - 1] < B[pb - 1]) {
			//截取A数组
			int[] temp = new int[m - pa];
			for(int i = pa,j = 0;i < m;i++) {
				temp[j++] = A[i];
			}
			return getKthC(temp, m - pa, B, n, k - pa);
		}else if(A[pa - 1] > B[pb - 1]) {
			//截取B数组
			int[] temp = new int[n - pb];
			for(int i = pb,j = 0;i < n;i++) {
				temp[j++] = B[i];
			}
			return getKthC(A, m, temp, n - pb, k - pb);
		}else {
			//如果两个元素相等，因为是有序的，所以所有元素有序集合中两个元素之间的元素值都是相同的。
			return A[pa - 1];
		}
	}
	
	/**
	 * 得到两个有序数组(升序)的第k小数。
	 * java语言合适写法，不截取数组，记录其实索引。没有完全按照牛客网java写法。
	 * 时间复杂度在O(log(m + n))以内。
	 * 参考：<br>
	 * 牛客网leetcode专题MedianOfTwoSortedArrays题目的评论高赞C++写法思路，和java写法<br>
	 * @param A 数组。
	 * @param aStart A数组的查找起始索引。
	 * @param B 数组
	 * @param bStart B数组的查找起始索引。
	 * @param k 第k小的数
	 * @return
	 */
	public static double getKth(int[] A,int aStart,int[] B,int bStart,int k) {
		//如果开始索引大于等数组长度，则第k小的数就是另一个数组start + k - 1索引处的元素
		if(aStart >= A.length) {
			return B[bStart + k - 1];
		}
		if(bStart >= B.length) {
			return A[aStart + k - 1];
		}
		if(k == 1) {
			return Math.min(A[aStart], B[bStart]);
		}
		//各自数组有效范围的待比较的第pa、pb小的数。第k小的数在所有有效范围有序集合的pa元素、pb元素之间。
		int pa, pb;
		//A.length - aStart即m,B.length - bStart即n。相当于截取数组后的剩下的元素个数。
		//此方法没有在开头校验排序保证m < n，所以很大可能n < k/2，即n < pb，导致B[pb-1]数组下标越界异常。
		//所以在这里进行m，n的大小判断，做出相应处理。
		if((A.length - aStart) > (B.length - bStart)) {
			pb = Math.min(k/2, B.length - bStart);
			pa = k - pb;
		}else {
			pa = Math.min(k/2, A.length - aStart);
			pb = k - pa;
		}
		//待比较元素各自数组的索引
		int paIndex = aStart + pa - 1, pbIndex = bStart + pb - 1;
		if(A[paIndex] < B[pbIndex]) {
			//paIndex + 1相当于元素比较后，素组开始索引就不包含当前元素，需要+1。
			return getKth(A, paIndex + 1, B, bStart, k - pa);
		}else if(A[paIndex] > B[pbIndex]) {
			return getKth(A, aStart, B, pbIndex + 1, k - pb);
		}else {
			//如果两个元素相等，因为是有序的，所以所有元素有序集合中两个元素之间的元素值都是相同的。
			return A[aStart + paIndex];
		}
	}

}
