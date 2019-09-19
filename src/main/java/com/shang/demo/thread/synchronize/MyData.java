package com.shang.demo.thread.synchronize;


/**
 * 自定义的数据类
 */
public class MyData {

    private int j =0 ;

    public synchronized void add(){
        j++;
        System.out.println(Thread.currentThread().getName()+"j的值为:"+j);
    }

    public synchronized void decrease(){
        j--;
        System.out.println(Thread.currentThread().getName()+"j的值为:"+j);
    }

    public int getData(){
        return j;
    }
}
