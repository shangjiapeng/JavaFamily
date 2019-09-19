package com.shang.demo.thread;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 自定义回环
 * 栅栏  CyclicBarrier -等待至barrier 状态再全部同时执行
 */
public class MyCyclicBarrier {

    public static void main(String[] args) {
        int n =4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n);

        for (int i = 0; i < n; i++) {
            new Writer(cyclicBarrier).start();
        }
    }

    /**
     * 静态内部类
     */
    static class Writer extends Thread{
        //内部属性
        private CyclicBarrier cyclicBarrier;

        //构造方法
        public Writer(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier=cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"开始写入数据");
                Thread.sleep(5000); //以睡眠来模拟线程需要进行写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕,等待其他线程写入数据");

                cyclicBarrier.await();  //用来挂起当前线程,直至所有的线程都达到barrier状态,再同时执行后续任务.
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕,继续处理");
        }
    }
}
