package com.shang.demo.alogrithm;

/**
 * 快速排序
 * 选择一个关键值作为基准值。比基准值小的都在左边序列(一般是无序的)，
 * 比基准值大的都在右边(一般是无序的)。一般选择序列的第一个元素。
 * 一次循环:从后往前比较，用基准值和最后一个值比较，如果比基准值小的交换位置，
 * 如果没有 继续比较下一个，直到找到第一个比基准值小的值才交换。
 * 找到这个值之后，又从前往后开始比较，如果有比基准值大的，交换位置，
 * 如果没有继续比较下一个,直到找到第一个比基准值大的 值才交换。
 * 直到从前往后的比较索引>从后往前比较的索引，结束第一次循环，
 * 此时，对于基准值 来说，左右两边就是有序的了
 */
public class FastSort {

    public static void main(String[] args) {
        int[] array = {5, 3, 9, 4, 8, 1, 7, 2, 6};
        //选第一个元素作为基准值
        int[] array1 = fastSort(array, 0, array.length - 1);
        for (int i : array1) {
            System.out.printf("%d", i);
        }
    }

    /**
     * 从小到达排序
     * 第一次调用时一般: low =0 high=array.length-1,
     *
     * @param array int[] 需要被快速排序的数组
     * @param low   表示当前次循环起始位置索引值
     * @param high  表示当前次循环结束位置索引值
     */
    public static int[] fastSort(int[] array, int low, int high) {
        //选第一个元素作为基准值
        int start = low;  //start表示从前向后遍历起始位置的索引
        int end = high;  //start表示从前向后遍历结束位置的索引
        int key = array[start];
        while (end > start) {
            //第一步:从后向前比较,找到第一个比基准值小的数
            while (end > start && array[end] >= key) {
                //如果比基准值大,则向前移一位
                end--;
            }
            int temp = array[end];
            array[end] = array[start];
            array[start] = temp;
            //第二步:从前向后比较,找到第一个比基数大的数
            while (end > start && array[start] <= key) {
                //如果比基准值小,则向右移动一位
                start++;
            }
            temp = array[end];
            array[end] = array[start];
            array[start] = temp;
            //两层循环
            //递归
            if (start > low) {
                fastSort(array, low, start - 1);//左边的序列,从第一个索引位置到关键值索引-1
            }
            if (end < high) {
                fastSort(array, end + 1, high);//右边的序列,从关键值索引+1到最后一个
            }
        }
        return array;
    }
}
