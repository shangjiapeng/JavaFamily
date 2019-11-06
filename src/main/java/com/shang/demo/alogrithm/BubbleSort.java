package com.shang.demo.alogrithm;


/**
 * 冒泡排序法
 * (1)比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
 * (2)这样对数组的第 0 个数据到 N-1 个数据进行一次遍历后，最大的一个数据就“沉”到数组第 N-1 个位置。
 * (3)N=N-1，如果 N 不为 0 就重复前面二步，否则排序完成
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {5, 3, 9, 4, 8, 1, 7, 2, 6};
        int[] array1 = bubbleSort(array);
        for (int i : array1) {
            System.out.printf("%d", i);
        }
    }

    /**
     * 从小到达排序
     *
     * @param array int[]
     */
    public static int[] bubbleSort(int[] array) {
        //数组的长度
        int n = array.length;

        while (n != 0) {
            for (int i = 0; i < n - 1; i++) {
                //如果前面的数字比后面的数字大就交换位置
                if (array[i] > array[i + 1]) {
                    //交换
                    int temp;
                    temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                }
            }
            //关键
            n = n - 1;
        }
        return array;
    }
}
