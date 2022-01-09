package com.lisijietech.algorithm.module.permutationcombination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
 * 本质上是组合，n个元素取m个组合。目前用递归方式。有看到深度优先搜索算法，应该差不多。算法、思路、方式和细节待优化。
 * 参考： 网上有但太少，可能也能在leetcode或牛客上找到。
 * 这个博客是c语言版本，暴力算法
 * https://blog.csdn.net/qq_35510898/article/details/108666994
 * 
 * @author LiSiJie
 * @date 2022年1月9日 下午5:14:53
 */
public class HuaweiOJYingXiongLianMeng {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		String[] strArr = s.split(" ");
		int[] scores = new int[strArr.length];
		for(int i = 0;i < strArr.length;i++) {
			scores[i] = Integer.parseInt(strArr[i]);
		}
		System.out.println(combinationRecursion(scores, 5));
	}

	public static Integer combinationRecursion(int[] arr, int m) {
		int total = 0;
		for (int i = 0; i < arr.length; i++) {
			total += arr[i];
		}
		return combination(arr, m, 0, new ArrayList<>(), total, null);
	}

	public static Integer combination(int[] arr, int m, int start, List<Integer> group, int total, Integer result) {
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

}
