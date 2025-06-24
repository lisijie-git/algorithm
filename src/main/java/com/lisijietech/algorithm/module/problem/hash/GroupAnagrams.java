package com.lisijietech.algorithm.module.problem.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 字母异位词分组
 * 
 * 1. 描述
 * 给你一个字符串数组，请你将字母异位词组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词（Anagram）：字母异位词是通过重新排列不同单词或短语的字母而形成的单词或短语，并使用所有原字母一次。
 * 示例1
 * 输入:strs = ["eat","tea","tan","ate","nat","bat"]
 * 输出:[["bat"],["nat","tan"],["ate","eat","tea"]]
 * 解释
 * 在strs中没有字符串可以通过重新排列来形成"bat"。
 * 字符串"nat"和"tan"是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串"ate"，"eat"和"tea"是字母异位词，因为它们可以重新排列以形成彼此。
 * 示例2
 * 输入:strs = [""]
 * 输出:[[""]]
 * 示例3
 * 输入:strs = ["a"]
 * 输出:[["a"]]
 * 提示
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i]仅包含小写字母
 * 
 * 2. 解析
 * 2.1 leetcode中文官方
 * 两个字符串互为字母异位词，当且仅当两个字符串包含的字母相同。
 * 同一组字母异位词中的字符串具备相同点，可以使用相同点作为一组字母异位词的标志，使用哈希表存储每一组字母异位词，哈希表的键为一组字母异位词的标志，哈希表的值为一组字母异位词列表。
 * 遍历每个字符串，对于每个字符串，得到该字符串所在的一组字母异位词的标志，将当前字符串加入该组字母异位词的列表中。遍历全部字符串之后，哈希表中的每个键值对即为一组字母异位词。
 * 官方使用了两种方法解题（具体分析看代码）。分别使用排序和计数作为哈希表的键。
 * 方式一，排序
 * 由于互为字母异位词的两个字符串包含的字母相同，因此对两个字符串分别进行排序之后得到的字符串一定是相同的，故可以将排序之后的字符串作为哈希表的键。
 * 方法二，计数
 * 由于互为字母异位词的两个字符串包含的字母相同，因此两个字符串中的相同字母出现的次数一定是相同的，故可以将每个字母出现的次数使用字符串表示，作为哈希表的键。
 * 由于字符串只包含小写字母，因此对于每个字符串，可以使用长度为26的数组记录每个字母出现的次数。
 * 需要注意的是，在使用数组作为哈希表的键时，不同语言的支持程度不同，因此不同语言的实现方式也不同。
 * 2.2 个人理解
 * 本质
 * 本质是分组。每个字符串，其包含字母相同就分为一组。字母异位词（Anagram），就是字母相同，字母顺序不一样的词语。
 * 核心
 * 核心是元素的分组标识和分组效率。分组标识就是元素属于哪一个组，分组效率就是元素查找放入所属分组的速度。
 * 
 * 3. 算法思路
 * 根据leetcode官方解析，其他大神解析，以及自己的思考理解，总结的大概算法思路。
 * 1 遍历每一个字符串元素。
 * 2 计算出元素的分组标识。通过leetcode官方的排序、计数方法，以及其他大神的方法得到分组标识。
 * 3 元素通过分组标识查找放入对应组。分组标识和元素是映射关系，并且分组标识大概率是非连续性的。所以用Map集合存储查找分组标识和分组元素比较合适。用List存储同组元素。
 * 4 转换输出模型。把对象转换成符合要求的结构对象。即Map转换成List。
 * 
 * @author LiSiJie
 * @date 2025年6月21日 10:43:48
 */
public class GroupAnagrams {
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：排序方式
	 * 写法：leetcode官方
	 * 
	 * 复杂度分析
	 * 时间复杂度
	 * O(nklogk)，其中n是strs中的字符串的数量，k是strs中的字符串的的最大长度。
	 * 需要遍历n个字符串，对于每个字符串，需要O(klogk)的时间进行排序以及O(1)的时间更新哈希表，因此总时间复杂度是O(nklogk)。
	 * 空间复杂度
	 * O(nk)，其中n是strs中的字符串的数量，k是strs中的字符串的的最大长度。需要用哈希表存储全部字符串。
	 * 
	 * @param strs
	 * @return
	 */
	public List<List<String>> groupAnagrams11(String[] strs) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String str : strs) {
			char[] array = str.toCharArray();
			Arrays.sort(array);
			String key = new String(array);
			List<String> list = map.getOrDefault(key, new ArrayList<String>());
			list.add(str);
			map.put(key, list);
		}
		return new ArrayList<List<String>>(map.values());
	}
	
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：排序方式
	 * 写法：其他大神
	 * 一行代码就搞定了，不可思议。
	 * 
	 * @param strs
	 * @return
	 */
	public List<List<String>> groupAnagrams12(String[] strs) {
		return new ArrayList<>(Arrays.stream(strs)
				.collect(Collectors.groupingBy(s -> s.chars().sorted()
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString()))
				.values());
	}
    
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：排序方式
	 * 写法：个人
	 * 个人水平长期未提升，写法比较传统古早。
	 * 
	 * @param strs
	 * @return
	 */
	public List<List<String>> groupAnagrams13(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String str : strs) {
			char[] cs = str.toCharArray();
			Arrays.sort(cs);
			String key = new String(cs);
			if (map.containsKey(key)) {
				map.get(key).add(str);
			} else {
				List<String> group = new ArrayList<>();
				group.add(str);
				map.put(key, group);
			}
		}
		return new ArrayList<>(map.values());
	}
	
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：计数方式
	 * 写法：leetcode官方
	 * 
	 * 复杂度分析
	 * 时间复杂度
	 * O(n(k+∣Σ∣))，其中n是strs中的字符串的数量，k是strs中的字符串的的最大长度，Σ是字符集，在本题中字符集为所有小写字母，∣Σ∣=26。
	 * 需要遍历n个字符串，对于每个字符串，需要O(k)的时间计算每个字母出现的次数，O(∣Σ∣)的时间生成哈希表的键，以及O(1)的时间更新哈希表，因此总时间复杂度是O(n(k+∣Σ∣))。
	 * 空间复杂度
	 * O(n(k+∣Σ∣))，其中n是strs中的字符串的数量，k是strs中的字符串的最大长度，Σ是字符集，在本题中字符集为所有小写字母，∣Σ∣=26。
	 * 需要用哈希表存储全部字符串，而记录每个字符串中每个字母出现次数的数组需要的空间为O(∣Σ∣)，在渐进意义下小于O(n(k+∣Σ∣))，可以忽略不计。
	 * 
	 * @param strs
	 * @return
	 */
	public List<List<String>> groupAnagrams21(String[] strs) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String str : strs) {
			int[] counts = new int[26];
			int length = str.length();
			for (int i = 0; i < length; i++) {
				counts[str.charAt(i) - 'a']++;
			}
			// 将每个出现次数大于 0 的字母和出现次数按顺序拼接成字符串，作为哈希表的键
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 26; i++) {
				if (counts[i] != 0) {
					sb.append((char) ('a' + i));
					sb.append(counts[i]);
				}
			}
			String key = sb.toString();
			List<String> list = map.getOrDefault(key, new ArrayList<String>());
			list.add(str);
			map.put(key, list);
		}
		return new ArrayList<List<String>>(map.values());
	}
	
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：质数方式
	 * 写法：其他大神
	 * 
	 * 在设计哈希函数的时候，经常用到质数，质数有个性质：每个大于1的整数都能唯一地分解为质数的乘积（不考虑顺序），也叫「算术基本定理」。
	 * 于是我们可以把每个字母分配一个质数，例如a=2,b=3,c=5...依次对应起来，对于每个单词，计算字母对应质数的乘积，
	 * 作为「哈希表」的键，这样字符种类相同且每个字符出现次数相同的单词就都映射到同一个键了。
	 * 接下来还要解决的问题是：乘积有可能很大，计算乘积有可能溢出。解决办法是：因此每一次乘法，都模一个较大的质数1e9 + 7。
	 * 可能大家还有疑惑？为什么模这个较大质数以后，不是字母异位词的哈希值仍是不同的？
	 * 这是因为1e9 + 7这个数值很大（10亿），「力扣」的测试用例1 <= strs.length <= 104，不是字母异位词乘积取大质数模得到的哈希值仍然相同的概率很低很低。
	 * 类似于在快速排序中，随机化切分元素以后，每一次选择切分元素恰好都是当前区间的最小值的概率极低，但理论上仍存在这种最坏情况。
	 * 复杂度分析
	 * 时间复杂度
	 * O(nk)，其中n是字符串数组长度，k是字符串最大长度。
	 * 空间复杂度
	 * O(nk)，用于存储哈希表和结果。
	 * 本题总结
	 * 本解法使用哈希表来分组字母异位词，核心在于设计一个能够唯一标识每组异位词的哈希函数。
	 * 虽然该哈希函数有可能造成哈希冲突，在理论上还是存在，由于1e9 + 7是一个很大的质数，「哈希冲突」的概率极低，因此可以通过系统测评。
	 * 
	 * @param strs
	 * @return
	 */
	public List<List<String>> groupAnagrams31(String[] strs) {
		// 为26个小写字母分配唯一的质数
		int[] PRIMES = new int[] {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
		// 防止整数溢出的模数
		long MOD = (long) 1e9 + 7;
		Map<Long, List<String>> map = new HashMap<>();
		for (String str : strs) {
			long hash = 1;
			for (char c : str.toCharArray()) {
				hash = (hash * PRIMES[c - 'a']) % MOD;
			}
			map.computeIfAbsent(hash, k -> new ArrayList<>()).add(str);
		}
		return new ArrayList<>(map.values());
	}
	
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：质数方式
	 * 写法：其他大神
	 * 这种初始化List集合的写法以后研究。new ArrayList<String>() {{add(s);}}
	 * 
	 * 考虑到数值溢出。质数乘积数值过大，小容量数据类型数值溢出。使用Long长整型存储数值，但未完全解决，数值太大还是有可能溢出。异常时，仅仅捕捉异常打印。
	 * 
	 * @param strs
	 * @return
	 */
	public List<List<String>> groupAnagrams32(String[] strs) {
		int[] primes = new int[] {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
		List<List<String>> res = new ArrayList<>();
		Map<Long, List<String>> map = new HashMap<>();
		for (int i = 0; i < strs.length; i++) {
			String s = strs[i];
			Long hashnumber = 1l;
			for (int j = 0; j < s.length(); j++) {
				try {
					hashnumber *= primes[s.charAt(j) - 'a'];
				} catch (Exception e) {
					System.out.println(j);
					System.out.println(s.charAt(j));
				}
			}
			if (map.containsKey(hashnumber)) {
				map.get(hashnumber).add(s);
			} else {
				map.put(hashnumber, new ArrayList<String>() {
					{
						add(s);
					}
				});
			}
		}
		res = map.values().stream().collect(Collectors.toList());
		return res;
	}
	
	/**
	 * 字母异位词分组
	 * 
	 * 分组标识：质数方式
	 * 写法：其他大神
	 * 
	 * 未考数值溢出。虑质数乘积数值过大，小容量数据类型数值溢出。
	 * 
	 * @param strs
	 * @return
	 */
    public List<List<String>> groupAnagrams33(String[] strs) {
        int[] prime = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
        Map<Integer,List<String>> map = new HashMap<>();
        for (String str : strs) {
            int key = 1;
            for(char c : str.toCharArray()){
                key *= prime[c - 'a'];
            }
            if (!map.containsKey(key)) map.put(key, new ArrayList<String>());
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }
    
    public String[] generateData(){
    	Random r = new Random();
    	//随机生成字符串数组长度，范围[1,10000]
    	int len = r.nextInt(10000) + 1;
    	String[] ss = new String[len];
    	for(int i = 0;i < len;i++) {
    		//随机生成字符串长度，范围[0,100]
    		int slen = r.nextInt(101);
    		char[] cs = new char[slen];
    		for(int j = 0;j < slen;j++) {
    			//随机生成小写字母
    			cs[j] = (char)(r.nextInt(26) + 97);
    		}
    		ss[i] = new String(cs);
    	}
		return ss;
    }
    
    public static void main(String[] args) {
    	GroupAnagrams ga = new GroupAnagrams();
    	//生成数据
    	String[] ss = ga.generateData();
    	System.out.println("输入数据长度：" + ss.length);
//		System.out.println(Arrays.toString(ss));
		//测试算法
		List<List<String>> result = new ArrayList<>();
		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		result = ga.groupAnagrams11(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法11结果分组长度：" + result.size());
//		result.forEach(System.out::print);
		
		start = System.currentTimeMillis();
		result = ga.groupAnagrams12(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法12结果分组长度：" + result.size());
		
		start = System.currentTimeMillis();
		result = ga.groupAnagrams13(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法13结果分组长度：" + result.size());
		
		start = System.currentTimeMillis();
		result = ga.groupAnagrams21(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法21结果分组长度：" + result.size());
		
		start = System.currentTimeMillis();
		result = ga.groupAnagrams31(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法31结果分组长度：" + result.size());
		
		start = System.currentTimeMillis();
		result = ga.groupAnagrams32(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法32结果分组长度：" + result.size());
		
		start = System.currentTimeMillis();
		result = ga.groupAnagrams33(ss);
		end = System.currentTimeMillis();
		System.out.printf("耗时%d毫秒\n",end - start);
		System.out.println("方法33结果分组长度：" + result.size());
	}
}
