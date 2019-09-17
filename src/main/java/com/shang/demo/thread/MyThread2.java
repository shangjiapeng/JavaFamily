package com.shang.demo.thread;

import java.util.concurrent.CountDownLatch;

/**
 * <p>自定义线程类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-09-03 16:07
 */
public class MyThread2 implements Runnable {

    private Counter counter;
    private CountDownLatch countDownLatch;

    public MyThread2(Counter counter, CountDownLatch countDownLatch) {
        this.counter = counter;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        //每个线程向Counter 中进行10000次累加
        for (int i = 0; i < 10000; i++) {
            counter.addCount();
        }
        //完成一个子线程
        countDownLatch.countDown();
    }
}
