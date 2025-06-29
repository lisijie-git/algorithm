package com.lisijietech.algorithm.module.base.sort;

import java.util.Arrays;

/**
 * 快速排序
 * 
 * AI讲解
 * 背景
 * 快速排序（Quick Sort）是一种非常高效的排序算法，由C.A.R.Hoare在1960年提出。
 * 思路
 * 它使用分治法（Divide and Conquer）策略来把一个序列分为较小和较大的两个子序列，然后递归地排序两个子序列。
 * 快速排序的基本思想是选择一个元素作为“基准”（pivot），然后将数组分成两部分：一部分包含所有小于基准的元素，另一部分包含所有大于基准的元素。
 * 然后，递归地在基准的左右两侧分别进行同样的操作。
 * 
 * @author LiSiJie
 * @date 2025年6月25日 15:33:52
 */
public class QuickSort {

	/**
	 * 快速排序。
	 * 
	 * 递归方式。
	 * 分区方法思路：单指针方法。
	 * 
	 * 核心
	 * 1 选取基准值
	 * 2 分区
	 * 3 递归
	 * 
	 * 复杂度分析
	 * 时间复杂度
	 * 平均情况：O(nlogn)
	 * 最坏情况：O(n^2)
	 * 
	 * 优点
	 * 由于其高效的平均性能和原地排序（不需要额外的存储空间），因此在实际应用中非常流行。
	 * 
	 * 优化
	 * 选择好的基准和优化分区策略可以显著提高其性能。
	 * 
	 * 参考
	 * 百度AI
	 * https://blog.csdn.net/wlddhj/article/details/131482951
	 * https://blog.csdn.net/weixin_44748929/article/details/142627216
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 */
	public void quickSort(int[] arr,int low,int high) {
		if(low < high) {
			// 把数组分成两部分，得到分区后的基准值下标。
			int pivotIndex = partition11(arr, low, high);
			// 递归对基准左边的部分进行快速排序。左边部分每个元素的值都小于等于基准值。
			quickSort(arr, low, pivotIndex - 1);
			// 递归对基准右边的部分进行快速排序。右边部分每个元素的值都大于等于基准值。
			quickSort(arr, pivotIndex + 1, high);
		}
	}
	
	/**
	 * 分区方法。
	 * 
	 * 单指针方法。可以理解成最简单的遍历。
	 * 
	 * 思路
	 * 此方法用于将数组分为两部分，一部分包含小于基准的元素，另一部分包含大于基准的元素。
	 * 基准元素被放置在正确的位置上，并返回该位置。
	 * 基准的选择可以是数组的第一个元素、最后一个元素、中间元素或者随机选择一个元素。这里我们选择最后一个元素作为基准。
	 * 过程
	 * 当发现一个元素小于或等于基准时，它与小于基准的左侧最新元素的下一个元素交换。这样，小于基准的元素都移到了左侧，大于基准的元素都移到了右侧。
	 * 最后，将基准元素放到左侧和右侧之间的中间位置。
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 * @return 枢轴下标，中心点下标，即分区后基准值下标。
	 */
	public int partition11(int[] arr,int low,int high) {
		// 选择最右边的元素作为基准值
		int pivot =  arr[high];
		// 将i初始化为low - 1，用于跟踪较小元素索引。
		// 其实就是小于基准值的元素，被遍历到后，它应该放在哪个索引位置，用i来记录。算法是使用交换的方式来放置。
		// 算法会把小于基准值的元素连续的放在数组左侧，所以其实排序完成时，i + 1就是基准值应该放置的位置。
		int i = low - 1;
		// 遍历索引的最大边界是j < high，因为arr[high]被选作基准元素，无论如何最终都要放在枢轴位置。
		// 如果基准元素被包含在循环遍历中，那最大边界就是就j <= high，最后一次循环是无效的。
		/* 
		 * 额外思考：
		 * 修改后思路分析
		 * 如果大边界就是j <= high，把循环中的判断改成arr[j] <= pivot。
		 * 因为arr[high]基准元素已知是最后一个元素，循环遍历交换完成后，基准元素就恰好在枢轴位置。
		 * 但是，如果有y个元素的值与基准元素相同，那就会有对应y - 1次交换元素操作的性能消耗，以及<=与<操作符的性能差异。
		 * 以及与枢轴相同值的元素会在枢轴元素的左边（原来则是会在右边）。
		 * 枢轴左边进行递归分区时，则会有y-1次最坏情况的遍历排序，即无效遍历，元素并不会改变位置，直到相同基准值元素被分区完。
		 * 原来思路分析
		 * 而按照原来的算法，与枢轴相同值元素会在枢轴元素的右边。
		 * 枢轴右边进行递归分区时，每次遍历在枢轴相同值元素上都是无效的。
		 * 优化
		 * 排除枢轴元素相同值的元素，再进行递归分区。待以后研究，这样是否会优化算法。
		 * 
		 */
		for(int j = low;j < high;j++) {
			// 如果当前元素arr[j]小于基准值，则增加i，并交换arr[i]和arr[j]
			if(arr[j] < pivot) {
				i++;
				swap(arr, i, j);
			}
			// 当前元素值>=基准值，则当前元素应该放在右边，即较小元素的最新索引的右边。较小元素索引最新索引i没有增加，即i < j，继续遍历即可。
		}
		// 将基准元素交换到枢轴（中心轴）位置，即arr[high]与索引i + 1处的元素交换。
		// 确保枢轴位置元素的左边是较小元素，右边是较大元素。
		swap(arr, i + 1, high);
		// 返回枢轴索引
		return i + 1;
	}
	
	/**
	 * 交换数组中的两个元素。
	 * 
	 * @param arr 数组
	 * @param a 最小索引
	 * @param b 最大索引
	 */
	public void swap(int[] arr,int a,int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	/**
	 * 快速排序。简单优化。
	 * 主要对分区写法的优化。优化提升可忽略不计。
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 */
	public void quickSort12(int[] arr,int low,int high) {
		if(low < high) {
			// 把数组分成两部分，得到分区后的基准值下标。
			int pivotIndex = partition12(arr, low, high);
			// 递归对基准左边的部分进行快速排序。左边部分每个元素的值都小于等于基准值。
			quickSort12(arr, low, pivotIndex - 1);
			// 递归对基准右边的部分进行快速排序。右边部分每个元素的值都大于等于基准值。
			quickSort12(arr, pivotIndex + 1, high);
		}
	}
	
	/**
	 * 分区方法。简单优化。
	 * 
	 * 不知道为什么网上都是写int i = low - 1这种。完全可以改变写法，达到优化。虽然优化的提升可以忽略不计，哈哈。
	 * 
	 * 优化
	 * i变量由low-1，变为low。
	 * 其意义原来是小于基准值的最新元素索引，现在意义是基准元素索引。
	 * 代码改动
	 * int i = low(原来为int i = low - 1)
	 * 基准值比较交换时，i++放在其后。（原来时放在交换前）
	 * 基准元素交换到枢轴位置时，枢轴索引为i。（原来为i + 1）
	 * 返回的枢轴索引也是i。（原来为i + 1）
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 * @return 枢轴下标，中心点下标，即分区后基准值下标。
	 */
	public int partition12(int[] arr,int low,int high) {
		int pivot =  arr[high];
		// 小于基准值的最新元素的下一个元素，即基准元素索引。
		int i = low;
		for(int j = low;j < high;j++) {
			// 如果当前元素arr[j]小于基准值，交换arr[i]和arr[j]，然后增加i
			if(arr[j] < pivot) {
				swap(arr, i, j);
				i++;
			}
		}
		// 将基准元素交换到枢轴（中心轴）位置，即arr[high]与索引i处的元素交换。
		swap(arr, i, high);
		// 返回枢轴索引
		return i;
	}
	
	/**
	 * 分区方法。
	 * 
	 * 测试个人想法思路。
	 * 网络上大多数的但指针分区方法，都是把基准元素的枢轴位置交换，放到循环方法外。可以放到循环方法里，减少一行代码的书写。
	 * 这样从人类感官感受来，得到种肤浅美感的享受。
	 * 虽然实际会徒增算法耗时，哈哈哈。
	 * 
	 * 分析
	 * 修改后
	 * 基准元素被包含在循环遍历中，即最大边界就是就j <= high，最后一次循环是无效的。
	 * 如果大边界就是j <= high，把循环中的判断改成arr[j] <= pivot。
	 * 因为arr[high]基准元素已知是最后一个元素，循环遍历交换完成后，基准元素就恰好在枢轴位置。
	 * 但是，如果有y个元素的值与基准元素相同，那就会有对应y - 1次交换元素操作的性能消耗，以及<=与<操作符的性能差异。
	 * 以及与枢轴相同值的元素会在枢轴元素的左边（原来则是会在右边）。
	 * 枢轴左边进行递归分区时，则会有y-1次最坏情况的遍历排序，即无效遍历，元素并不会改变位置，直到相同基准值元素被分区完。
	 * 未修改
	 * 而按照原来的算法，与枢轴相同值元素会在枢轴元素的右边。
	 * 枢轴右边进行递归分区时，每次遍历在枢轴相同值元素上都是无效的。
	 * 
	 * 优化
	 * 排除枢轴元素相同值的元素，再进行递归分区。待以后研究，这样是否会优化算法。
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 * @return 枢轴下标，中心点下标，即分区后基准值下标。
	 */
	public int partition13(int[] arr,int low,int high) {
		int pivot =  arr[high];
		// 小于基准值的最新元素的下一个元素，即基准元素索引
		int i = low;
		for(int j = low;j <= high;j++) {
			// 如果当前元素arr[j]小于基准值，交换arr[i]和arr[j]，然后增加i
			if(arr[j] <= pivot) {
				swap(arr, i, j);
				i++;
			}
		}
		// 返回枢轴索引。由于循环遍历包含了基准元素，交换时索引多加了1，需要减去。
		return i - 1;
	}
	
	/**
	 * 快速排序。
	 * 
	 * 递归方式。
	 * 分区方法思路：双指针对撞方法。
	 * 
	 * 参考
	 * https://blog.csdn.net/wyx1473343124/article/details/134550193
	 * https://blog.csdn.net/G81948577/article/details/140450501
	 * https://baike.baidu.com/item/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95/369842
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 */
	public void quickSort21(int[] arr,int low,int high) {
		// 递归终止条件
		/*
		 * 思考，通过>=判断终止条件，通过return跳出方法终止代码的效率高；还是<判断符合条件，跳过{}方法体，自然执行完剩余代码跳出方法效率高？
		 * 可能这是个人书写习惯，或者结构思维的差异。
		 * 可能存在微小的性能差异。细小差异在巨量的元素数据中可能有一定影响。
		 */
		if(low >= high) {
			return;
		}
		// 把数组分成两部分，得到分区后的基准值下标。
		int pivotIndex = partition21(arr, low, high);
		// 递归对基准左边的部分进行快速排序。左边部分每个元素的值都小于等于基准值。
		quickSort21(arr, low, pivotIndex - 1);
		// 递归对基准右边的部分进行快速排序。右边部分每个元素的值都大于等于基准值。
		quickSort21(arr, pivotIndex + 1, high);
	}
	
	/**
	 * 分区方法。
	 * 
	 * 双指针对撞法。
	 * 双指针对撞法对比单指针法，减小无效交换。
	 * 单指针，即使排序顺序正确，在遍历时还是会把较小值元素往左侧交换。
	 * 双指针，排序顺序正确，会继续遍历下一个元素，不会进行无效交换。
	 * 
	 * 核心
	 * 1 选取基准值
	 * 本算法选取第一个元素。
	 * 其他：
	 * 百度AI讲解快速排序双指针法和单指针法性能上的区别上有提到
	 * 三数中值分割法。简单理解，选第一、中间、最后三个元素，选择出处于中间值的元素作为基准值，可以减小最坏情况。
	 * 2 分区
	 * 双指针对撞法。
	 * 3 递归
	 * 
	 * 复杂度分析
	 * 时间复杂度
	 * 平均情况：O(nlogn)
	 * 最坏情况：O(n^2)
	 * 
	 * 
	 * 
	 * @param arr 数组
	 * @param low 最小索引
	 * @param high 最大索引
	 * @return 枢轴下标，中心点下标，即分区后基准值下标。
	 */
	public int partition21(int[] arr,int low,int high) {
		int pivot = arr[low];
		int i = low;
		int j = high;
		while(i < j) {
			/*
			 * 右指针向左遍历。
			 * 从高索引位置向低索引位置循环遍历，判断小于等于基准值，并且左指针索引i < 右指针索引j。
			 * 判断为true，则继续向左遍历。
			 * 判断为false，则停止。
			 */
			while(arr[j] >= pivot && i < j) {
				j--;
			}
			/*
			 * 左指针向右遍历。
			 * 从低索引位置向高索引位置循环遍历，判断小于等于基准值，并且左指针索引i < 右指针索引j。
			 * 判断为true，则继续向右遍历。
			 * 判断为false，则停止。
			 */
			while(arr[i] <= pivot && i < j) {
				i++;
			}
			/*
			 * 左右指针元素交换。
			 * 当左右指针的遍历停止后，有几种情况：
			 * 1. 左右指针确定了元素值与基准值的大小关系，即右指针元素值 < 基准值 < 左指针元素值，这种情况交换元素是很正确的。
			 * 2. 左右指针碰撞，即i == j，同时也确定了元素值与基准值大小关系。
			 * 由于先执行右指针遍历，最终指针碰撞索引位置的元素值是<基准值。
			 * 当然，如果指针遍历的代码顺序反过来，即左指针遍历代码先执行，碰撞元素值是>基准值。
			 * 3. 左右指针碰撞，但是无法确定碰撞位置的元素值与基准值的大小关系。
			 * 即，右指针一直循环到索引low的位置，此时i == j。由于i < j的判断，右指针循环停止时，无法确定与基准值的大小关系。
			 * 同样，左指针从low开始循环时，此时已经是i == j。由于i < j的判断，左指针一次循环都没有就停止了，无法确定与基准值的大小关系。
			 * 算法优化弥补
			 * 因为此算法的写法利用率很好，选择了arr[low]为基准元素，恰好弥补了第3中情况的不确定性。
			 * 即，如果先执行右指针遍历，根据当前算法，最终指针碰撞索引位置的元素值是<=基准值；
			 * 反之，如果先执行左指针遍历，最终指针碰撞索引位置的元素值是>=基准值
			 * 优化思考
			 * 但是算法也有优化空间，这里的swap方法最后一次交换是无效的，以及pivot基准值的选择或算法等等。可以参考Arrays.sort源码。以后研究。
			 */
			swap(arr,i,j);
		}
		/*
		 * 将基准元素交换到枢轴（中心轴）位置，即arr[low]与索引i处的元素交换。
		 * 也可以写成swap(arr,low,i)。
		 * 枢轴位置的元素值与基准值的大小关系，是与左右指针代码执行顺序相关的。在指针遍历代码中的注释有个人理解。
		 */
		arr[low]  = arr[i];
		arr[i] = pivot;
		return i;
	}
	
	/**
	 * 快速排序。
	 * 主方法组装方式。
	 * 因为算法多种实现方式、优化方式，以及用于测试的实现方式。
	 * 关键部分抽离成方法，抽离出来的方法可以有多种实现方式，每一种都需要再写一个主方法来适配，就很麻烦。
	 * 把不同的抽离方法，通过函数式接口、Lambda表达式，生成接口实现类实例作为参数传给主方法。
	 * 
	 * 此方法用作测试多种实现方式的算法正确性比较合适。因为不知到函数式接口对性能影响有多大，测试算法耗时可能不准确。
	 * 
	 * @param arr
	 * @param low
	 * @param high
	 * @param p
	 */
	public void quickSortAssemble(int[] arr,int low,int high,Partition p) {
		if(low < high) {
			// 把数组分成两部分，得到分区后的基准值下标。
			int pivotIndex = p.part(arr, low, high);
			// 递归对基准左边的部分进行快速排序。左边部分每个元素的值都小于等于基准值。
			quickSortAssemble(arr, low, pivotIndex - 1, p);
			// 递归对基准右边的部分进行快速排序。右边部分每个元素的值都大于等于基准值。
			quickSortAssemble(arr, pivotIndex + 1, high, p);
		}
	}
	
	
	public static void main(String[] args) {
		QuickSort qs = new QuickSort();
		int[] arr = {1,2,3,4,5,3,10,9,8,6,7};
		qs.quickSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{1,2,3,4,5,3,10,9,8,6,7};
		qs.quickSort12(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{1,2,3,4,5,3,10,9,8,6,7};
		qs.quickSortAssemble(arr, 0, arr.length - 1,qs::partition13);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{1,2,3,4,5,3,10,9,8,6,7};
		qs.quickSort21(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}

}

/**
 * 函数式接口。有且仅有一个抽象方法的接口
 * 
 * @FunctionalInterface
 * 声明函数式接口的注解。用来约束当前接口必须是函数式接口。非必要。
 * 
 * 参考
 * https://blog.csdn.net/weixin_44456914/article/details/142728122
 * 
 * @author LiSiJie
 * @date 2025年6月27日 19:55:45
 */
@FunctionalInterface
interface Partition{
	int part(int[] arr,int low,int high);
}
