package com.lisijietech.algorithm.module.problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
给你一个整数 n，请返回长度为 n 、仅由元音 (a, e, i, o, u) 组成且按 字典序排列 的字符串数量。
字符串 s 按 字典序排列 需要满足：对于所有有效的 i，s[i] 在字母表中的位置总是与 s[i+1] 相同或在 s[i+1] 之前。

示例 1：
输入：n = 1
输出：5
解释：仅由元音组成的 5 个字典序字符串为 ["a","e","i","o","u"]

示例 2：
输入：n = 2
输出：15
解释：仅由元音组成的 15 个字典序字符串为
["aa","ae","ai","ao","au","ee","ei","eo","eu","ii","io","iu","oo","ou","uu"]
注意，"ea" 不是符合题意的字符串，因为 'e' 在字母表中的位置比 'a' 靠后

示例 3：
输入：n = 33
输出：66045

提示：
1 <= n <= 50 
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/count-sorted-vowel-strings
*/

/**
 * 
 * @author LiSiJie
 * @date 2023年4月12日 下午1:27:47
 */
public class CountSortedVowelStrings {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		System.out.println(countRecursion(n, (char) 0, new char[] {'a','e','i','o','u'}));
	}
	
	/**
	 * 递归方法
	 * @param n 字符串长度
	 * @param pre 前一个字符
	 * @param arr 字符数组，问题涉及的字符范围。此题目是 a e i o u 五个元音字母
	 * @return
	 */
	public static int countRecursion(int n,char pre,char[] arr) {
		if(n < 1) {
			return 1;
		}
		int count = 0;
		for(char c : arr) {
			if(c >= pre) {
				count += countRecursion(n - 1, c, arr);
			}
		}
		return count;
	}
}
