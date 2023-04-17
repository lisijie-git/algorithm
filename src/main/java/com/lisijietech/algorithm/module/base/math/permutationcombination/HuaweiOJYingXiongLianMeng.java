package com.lisijietech.algorithm.module.base.math.permutationcombination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 华为OJ,英雄联盟 题目描述： 英雄联盟是一款十分火热的对战类游戏。每一场对战有10位玩家参与，分为两组，每组5人。
 * 每位玩家都有一个战斗力，代表着这位玩家的厉害程度。为了对战尽可能精彩，我们需要把玩家们分为实力相等的两组。
 * 一组的实力可以表示为这一组5位玩家的战斗力和。现在，给你10为玩家的战斗力，请你把他们分为实力尽量相等的两组。 请你输出这两组的实力差。
 * 解答要求 时间限制：1000ms，内存限制：64MB
 * 输入 10个整数（范围在[1,10000]之间）
 * 输出 最小实力差距 
 * 样例：
 * 输入 1 2 3 4 5 6 7 8 9 10
 * 输出 1 
 * 思路：
 * 本质上是组合，n个元素取m个组合。目前用递归方式。
 * 网上有看到深度优先搜索算法，应该差不多，先走到叶子节点最深层得到结果，以免占用太多空间。
 * 算法、思路、方式和细节待优化。
 * 优化：
 * 1、（待实现）
 * 因为是10个元素取一半5个元素，求这五个与剩下五个的差值，
 * 所以当取得的组合恰好是之前已经取到过的组合的剩下五个，那差值也是一样的。
 * 那么，只需要一半数量的组合方式，且这一半数量的组合方式中没有互相是剩余组合方式的情况，那就可以确定所有差值情况。
 * 
 * 参考： 网上有但太少，可能也能在leetcode或牛客上找到。
 * 这个博客是c语言版本，暴力算法
 * https://blog.csdn.net/qq_35510898/article/details/108666994
 * 
 * @author LiSiJie
 * @date 2022年1月9日 下午5:14:53
 */
public class HuaweiOJYingXiongLianMeng {

	public static void main(String[] args) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String s = br.readLine();
//		String[] strArr = s.split(" ");
//		int[] scores = new int[strArr.length];
//		for(int i = 0;i < strArr.length;i++) {
//			scores[i] = Integer.parseInt(strArr[i]);
//		}
//		System.out.println(combinationRecursion(scores, 5));
		
		int[] arr = getInputSteamArray(10);
		int result = combination(arr,5,0,0,getSum(arr));
		System.out.println(result);
	}

	public static Integer combinationRecursion(int[] arr, int m) {
		return combination(arr, m, 0, new ArrayList<>(), getSum(arr), null);
	}

	/**
	 * 排列组合，未优化方法，最开始写的思路不太清晰。
	 * 打印了每个组合的元素值，差值。
	 * 
	 * @param arr
	 * @param m
	 * @param start
	 * @param group
	 * @param total
	 * @param result
	 * @return
	 */
	public static Integer combination(int[] arr, int m, int start, List<Integer> group, int total, Integer result) {
		//递归调用结束语句。
		//个人认为应该在当层能结束递归时就结束递归，不应该多一层递归只用来结束，徒增功耗，
		//递归语句和结束语句只执行其中一个。
		if(m < 1) {
			return result;
		}
		int end = arr.length - m + 1;
		int score = 0;
		for (int i = start; i < end; i++) {
			List<Integer> nGroup = new ArrayList<>();
			nGroup.addAll(group);
			nGroup.add(arr[i]);
			result = combination(arr, m - 1, i + 1, nGroup, total, result);
			if (m < 2) {
				for (Integer e : nGroup) {
					score += e;
				}
				int difference = Math.abs(score - (total - score));
				result = result == null || difference < result ? difference : result;
				System.out.println(nGroup);
				System.out.println("差距是：" + difference);
			}
		}
		return result;
	}
	
	public static int[] getInputSteamArray(int length) {
		// System.in输入流是系统方法一般不需要关闭，因为关闭后无法再次开启(待确定)，所以Scanner不需要调用close方法。
		Scanner sc = new Scanner(System.in);
		int[] arr = new int[length];
		for(int i = 0;sc.hasNextInt();i++) {
			arr[i] = sc.nextInt();
		}
		return arr;
	}
	
	public static int getSum(int[] arr) {
		int sum = 0;
		for(int i : arr) {
			sum += i;
		}
		return sum;
	}
	
	/**
	 * 排列组合,递归方式，优化后。
	 * 还可以优化。因为是10个元素取一半5个元素，求这五个与剩下五个的差值，
	 * 所以当取得的组合恰好是之前已经取到过的组合的剩下五个，那差值也是一样的。
	 * 那么，只需要一半数量的组合方式，且这一半数量的组合方式中没有互相是剩余组合方式的情况，那就可以确定所有差值情况。
	 * 
	 * @param n 元素数组，也相当与组合中的n
	 * @param m 组合中的m，或深度优先搜索的深度
	 * @param start 起始索引。
	 * @param score 取出组合的分数。
	 * @param sum 整个数组的分数。参数可优化为成员属性
	 * @return
	 */
	public static int combination(int[] n,int m,int start,int score,int sum) {
		int difference = -1;
		int newDiff = 0;
		int curScore = 0;
		for(int i = start;i <= n.length - m;i++) {
			curScore = score + n[i];
			if(m < 2) {
				newDiff = Math.abs(2 * curScore - sum);
			}else {
				newDiff = combination(n, m - 1, i + 1, curScore, sum);
			}
			difference = difference < newDiff && difference != -1 ? difference : newDiff;
		}
		return difference;
	}
}
