package com.shang.demo.alogrithm;

/**
 * 插入排序算法
 * 通过构建有序序列，对于未排序数据，在已排序序列中从右后向左扫描，找到相应的位置并插入
 * 如果输入数组已经是排好序的话，插入排序出现最佳情况，其运行时间是输入规模的一个线性函数。
 * 如果输入数组是逆序排列的，将出现最坏情况。平均情况与最坏情况一样，其时间代价是(n2)
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] array = {5, 3, 9, 4, 8, 1, 7, 2, 6};
        int[] array1 = insertSort(array);
        for (int i : array1) {
            System.out.printf("%d", i);
        }
    }

    /**
     * 从小到达排序
     *
     * @param array int[]
     */
    public static int[] insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            //待插入的数字
            int insertVal = array[i];
            //和左边的数字比较,如果右边比左边小
            while (i > 0 && insertVal < array[i - 1]) {
                //将左边的数字向右移动
                array[i] = array[i - 1];
                //索引向前移动
                i--;
            }
            //这一步是关键
            array[i] = insertVal;
        }
        return array;
    }
}
