package com.shang.demo.javabasic.fanxing;

/**
 * 泛型知识点测试类
 * 泛型的本质是参数化类型,也就是说所操作的数据类型被指定为一个参数,
 * 类型通配符:
 * 一般是使用?代替具体的类型参数,例如List<?>
 * <? extend T> 表示该通配符所代表的类型是T类型的子类
 * <? super T> 表示该通配符所代表的类型是T类型的父类
 */
public class FanXing {

    public static void main(String[] args) {
        int[] inputArray = {1, 2, 3};
        printArray(new int[][]{inputArray});

    }

    /**
     * 泛型方法,根据接受到的不同类型的参数,做不同的处理
     * @param inputArray
     * @param <E>
     */
    public static<E> void printArray( E[] inputArray){
        for (E element : inputArray) {
            System.out.printf("%s",element);
        }
    }

    /**
     * 类型擦除
     * JAVA中的泛型基本都是在编译器这个层次,
     */

}
