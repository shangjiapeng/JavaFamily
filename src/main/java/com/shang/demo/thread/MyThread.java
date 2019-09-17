package com.shang.demo.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>自定义线程类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-09-03 16:07
 */
public class MyThread implements Runnable {

    private List<Object> dataList;
    private CountDownLatch countDownLatch;

    public MyThread(List<Object> dataList, CountDownLatch countDownLatch) {
        this.dataList = dataList;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        //每个线程向dataList中添加100个元素
        for (int i = 0; i < 100; i++) {
            dataList.add(new Object());
        }
        //完成一个子线程
        countDownLatch.countDown();
    }
}
