package com.shang.demo.alogrithm;


/**
 * 希尔排序算法原理:
 * 先将整改待排序的序列,分割成若干个子序列,然后分别进行直接插入排序,
 * 待各个子序列"基本有序"时,再对全部的记录进行好直接插入排序.
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] array = {5, 3, 9, 4, 8, 1, 7, 2, 6};
        int[] array1 = shellSort(array);
        for (int i : array1) {
            System.out.printf("%d", i);
        }
    }

    /**
     * 从小到达排序
     * 操作方法:
     * 1 选择一个增量序列 t1,t2,t3,...ti,tj...tk  其中ti>tj,tk=1;
     * 2 按增量序列个数k,对序列进行k趟排序
     * 3 每趟排序,根据对应的增量ti,将待排序列分割成若干长度为m的子序列,
     * 分别对各个子序列进行直接插入排序,当增量因子为1的时候,整个序列就只看做一个序列处理.
     *
     * @param array int[]
     */
    public static int[] shellSort(int[] array) {
        return null;


    }

}
