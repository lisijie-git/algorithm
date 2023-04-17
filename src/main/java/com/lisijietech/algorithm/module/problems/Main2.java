package com.lisijietech.algorithm.module.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 华为机试题。
 * 输入：
 * 4 人数，下面的人id为 1 2 3 4
 * 100 100 60 80 身高
 * 40 60 50 30 体重
 * 输出：
 * 2 1 4 3 按照身高从高到底，如果有相同，再按照体重，从重到轻，排序输出人的id号。
 * @author lisijie
 * @date 2020-7-29
 */
public class Main2 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			String str1 = in.nextLine();
			String str2 = in.nextLine();
			String str3 = in.nextLine();
			int n = Integer.parseInt(str1);
			String[] hStr = str2.split(" ");
			String[] wStr = str3.split(" ");
			int[] h = new int[n];
			int[] w = new int[n];
			int[] sort = new int[n];
			Map<Integer,Integer> map = new HashMap<>();
			for(int i = 0;i < n;i++) {
				h[i] = Integer.parseInt(hStr[i]);
				w[i] = Integer.parseInt(wStr[i]);
				sort[i] = h[i] * 100 + w[i];
				map.put(sort[i],i);
			}
			
			Arrays.sort(sort);
			
			String s = "";
			for(Integer i : sort) {
				s = s + (map.get(i)+1) + " ";
			}
			System.out.println(s.substring(0,s.length() - 1));
		}
	}
}
