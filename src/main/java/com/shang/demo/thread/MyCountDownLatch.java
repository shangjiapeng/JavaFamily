package com.shang.demo.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 自定义线程计数器-Demo
 */
public class MyCountDownLatch {

    public void countDownLatch() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
            latch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
            latch.countDown();
        }).start();

        System.out.println("等待两个子线程执行完毕");
        latch.await();

        System.out.println("两个子线程已经执行完毕");
        System.out.println("继续执行主线程");

    }


}
