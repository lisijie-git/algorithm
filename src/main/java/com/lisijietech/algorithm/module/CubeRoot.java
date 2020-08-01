package com.lisijietech.algorithm.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 计算一个数字的立方根。
 * @author lisijie
 * @date 2020-7-24
 */
public class CubeRoot {
	
	public static void main(String[] args) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String s = br.readLine();
//		double d = Double.parseDouble(s);
//		System.out.println(cubeRoot(d));
		System.out.println(cubeRootDemo(9));
	}
	
	/**
	 * 二分查找立方根demo。不是很完善，不建议使用。
	 * 简单暴力思路。
	 * 如果立方根是double类型偶数正整数可以获得准确结果。如果是奇数，double/2会得到小数，会一直二分下去，有精度误差。
	 * @param d
	 * @return
	 */
	public static double cubeRootDemo(double d) {
		//立方根
		double r = d;
		//立方
		double c = r * r * r;
		//剩余数
		double rem = d;
		
		int count = 0;
		while(rem > 0.000001) {
			count ++;
			
			if(c > d) {
				r -= rem/2;
			}else if(c < d) {
				r += rem/2;
			}else {
				break;
			}
			rem -= rem/2;
			c = r * r * r;
		}
		System.out.println(count);
		System.out.println(c);
//		System.out.println(Math.floor(c * 10) / 10);
//		return Math.floor(r * 10) / 10;
		return r;
	}
	
//	public static double cubeRoot(double d) {
//		//立方根
//		int r = (int) Math.floor(d);
//		//立方
//		int c;
//		//开始和结束范围
//		int start = 0;
//		int end = r;
//		
//		while(end - start > 0) {
//			c = r * r * r;
//			if(c > d) {
//				end = r;
//				r = (end + start)/2;
//			}else if(c < d) {
//				start = r;
//				r = (end + start)/2;
//			}else {
//				return r;
//			}
//		}
//		
//	}

}
