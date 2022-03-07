package com.lisijietech.algorithm.module.exercise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * 华为机试题。未完成ac。
 * 输入：
 * a1-a2,a2-a3,a5-a6 系统标识依赖关系
 * a2,a5 出故障的系统
 * 输出：
 * a3,a6 按先后顺序输出没有故障的系统
 * a-b，表示a依赖于b。b出故障，a也会挂掉。a出故障，b不会挂。
 * @author lisijie
 * @date 2020-7-29
 */
public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			String str1 = in.nextLine();
			String str2 = in.nextLine();
			String[] depends = str1.split(",");
			String[] errs = str2.split(",");
			Map<String,String> result = new LinkedHashMap<>();
			for(int i = 0;i < depends.length;i++) {
				String[] depend = depends[i].split("-");
				if(!result.containsKey(depend[0])) {
					result.put(depend[0], null);
				}
				for(int j = depend.length - 1;j > 0;j--) {
					result.put(depend[j], depend[j - 1]);
				}
			}
			for(String err : errs) {
				if(result.containsKey(err)) {
					String key = err;
					String value;
					do{
						value = result.get(key);
						result.remove(key);
						key = value;
					} while (value != null);
				}
			}
			StringBuilder sb = new StringBuilder();
			for(Entry<String,String> entry : result.entrySet()) {
				sb.append(entry.getKey()).append(",");
			}
			if(sb.length() > 0) {
				System.out.println(sb.substring(0, sb.length() - 1).toString());
			}else {
				System.out.println(",");
			}
		}
	}
}
