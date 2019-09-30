package com.shang.demo.alogrithm;

/**
 * 二分查找算法又叫折半查找，
 * 要求: 待查找的序列有序。每次取中间位置的值与待查关键字比较，
 * 如果中间位置 的值比待查关键字大，则在前半部分循环这个查找的过程，
 * 如果中间位置的值比待查关键字小， 则在后半部分循环这个查找的过程。
 * 直到查找到了为止，否则序列中没有待查的关键字。
 */
public class HalfSearch {

    public static void main(String[] args) {

        int[] array ={2,3,4,5,6,7,8,9};
        int a= 5;
        int i = biSearch(array, a);
        System.out.println("a在是数组的第"+i+"个数字");

    }

    public static int biSearch(int[]array,int a){
        int start =0; //最小的索引
        int end=array.length-1;//最大索引
        int mid;//中间索引

        while (start<=end){
            mid =(start+end)/2; //中间位置的索引
            if (array[mid]==a){
                return mid+1;
            }else if (array[mid]<a){//向右查找
                start=mid+1;
            }else {//向左查找
                end=mid-1;
            }
        }
        //如果经过了循环程序没有结束,就返回-1,代表没有这个值
        return -1;

    }
}
