package com.lisijietech.algorithm.module;

import java.util.Scanner;

/**
 * 两个正整数a和b的最小公倍数
 * @author lisijie
 * @date 2020-7-24
 */
public class LeastCommonMultiple {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//hasNextInt(radix),radix代表char类型的unicode码值，一般不用这个方法。如hasNextInt(20)代表下一个字符大小不能超过j
		//nextInt(radix)radix代表进制转换。如nextInt(20)，如果控制台输入11，会得到21
		//hasNext(pattern)pattern匹配字符串样式，下一个索引有匹配的字符串样式就返回true
		while(in.hasNextInt()) {
			int a = in.nextInt();
			int b = in.nextInt();
			System.out.println(lcm(a,b));
		}
	}
	
	/**
	 * 两个正整数a和b的最小公倍数算法。
	 * 思路原理，a和b的最小公倍数：a和b的积，除以a和b的最大公约数，a*b/gcd(a,b)。
	 * @param A
	 * @param B
	 * @return
	 */
	public static int lcm(int a,int b) {
		return a * b / gcd(a,b);
	}
	
	/**
	 * 两个正整数a和b的最大公约数。GreatestCommonDivisor
	 * 辗转相除法：
	 * a除以b，取余数r，r = a % b，则r的范围0 <= r < b。如果r>0,则用除数b记a1，除以余数r记b1，取余数r1, r1 = a1 % b1。
	 * 直到rn = 0。则bn为最大公约数。
	 * 原理不是很理解，如果能证明最大公约数d，属于余数remainder，d <= r，那就好理解了。
	 * @param a
	 * @param b
	 * @return
	 */
	public static int gcd(int a,int b) {
		//大小判断交换是为了减少一次循环或者递归，当 a < b 时，辗转相除第一次执行相当于交换，直接判断交换不用求余数，速度快一点点。
		//但是a > b 时，这大小判断就冗余。
		//所以这步判断不是必要的。
//		if(a < b) {
//			int temp = a;
//			a = b;
//			b = temp;
//		}
		//循环方式
		while(b != 0) {
			int r = a % b;
			a = b;
			b = r;
		}
		return a;
		//递归方式
//		if(b == 0) return a;
//		return gcd(b,a % b);
	}

}
