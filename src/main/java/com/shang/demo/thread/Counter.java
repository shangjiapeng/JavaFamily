package com.shang.demo.thread;

/**
 * <p>计数器类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-09-04 10:50
 */
public class Counter {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //addCount() 加上了synchronized关键字就可以编程线程安全的方法
    public synchronized void addCount() {
        count++;
    }
}
