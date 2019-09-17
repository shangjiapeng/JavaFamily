package com.shang.demo.thread;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>用于测试多线程知识</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-09-03 15:26
 */
public class ThreadTest {

    private volatile boolean exit =false;


    public static void main(String[] args) {
        //进行10次测试
        for (int i = 0; i < 10; i++) {
//            testThread();
            testThread2();
        }
    }

    /**
     *     非线程安全          线程安全
     *ArrayList/LinkedList   Vector
     *     HashMap           HashTable
     *   StringBuilder      StringBuffer
     */
    public static void testThread() {

        //用来测试的list
       // ArrayList 是非线程安全的,Vector 线程安全;HashMap 非线程安全,HashTable 线程安全;
        List<Object> dataList = new Vector<>();
        //让主线程等待100个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(100);
        //启动100个线程
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new MyThread(dataList, countDownLatch));
            thread.start();
        }

        try {
            //主线程等待所有的子线城执行完成之后,再向下执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(dataList.size());
    }

    public static void testThread2() {

        //初始化,计数器
        Counter counter = new Counter();
        //让主线程等待1000个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        //启动1000个线程
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new MyThread2(counter, countDownLatch));
            thread.start();
        }

        try {
            //主线程等待所有的子线城执行完成之后,再向下执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(counter.getCount());
    }

    public static void testThread3() {

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟3秒");
            }
        },3, TimeUnit.SECONDS);

        //使用lambda 表达式执行
        scheduledThreadPool.scheduleAtFixedRate(() -> System.out.println("延迟一秒后每3秒执行一次"),1,3,TimeUnit.SECONDS);

        scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("固定延迟3秒执行");
            }
        },3,3,TimeUnit.SECONDS);

    }

}
