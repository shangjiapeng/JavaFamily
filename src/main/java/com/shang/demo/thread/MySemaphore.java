package com.shang.demo.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 是一种基于计数的信号量 --- 控制同时访问的线程数目
 * 它可以设定一个阈值，基于此，多个线程竞争获取许可信
 * 号，做完自己的申请后归还，超过阈值后，线程申请许可信号将会被阻塞。Semaphore 可以用来
 * 构建一些对象池，资源池之类的，比如数据库连接池
 */
public class MySemaphore {

    public static void main(String[] args) {
//        test1();
        test2();

    }


    public static void test1() {
        //创建一个计数阈值为5的信号量对象,只能5个线程同事访问
        int permits= 5;
        Semaphore semaphore= new Semaphore(permits);
        try {  //申请许可
            permits = semaphore.availablePermits();//得到可用的许可数目

            semaphore.acquire();   //获取一个许可,如果没有就一直等待
            semaphore.acquire( permits);   //获取permits个许可,如果没有就一直等待
            semaphore.tryAcquire();  //尝试获取一个许可，若获取成功，则立即返回 true，若获取失败，则立即返回 false
            semaphore.tryAcquire(permits, 2000, TimeUnit.MILLISECONDS); //尝试获取 permits 个许可，若在指定的时间内获取成功，则立即返回 true，否则则立即返回 false

            System.out.println("执行业务逻辑!!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release(); //释放一个许可,注意释放之前必须先获取许可
            semaphore.release(permits); //释放permits个许可,注意释放之前必须先获取许可
        }
    }

    /**
     * 例如: 若一个工厂有 5 台机器，但是有 8 个工人，一台机器同时只能被一个工人使用，只有使用完
     * 了，其他工人才能继续使用。那么我们就可以通过 Semaphore 来实现
     */
    public static void test2() {
        int N=8;  //工人数目
        Semaphore semaphore = new Semaphore(5);

        for (int i = 1; i <= N; i++) {
            new Worker(i,semaphore).start();
        }


    }

    /**
     * 工人静态内部类
     */
    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {

                semaphore.acquire(); //获取一个许可
                System.out.println("工人"+this.num+"占用一个机器再生产");
                Thread.sleep(2000);

                semaphore.release();//释放一个许可
                System.out.println("工人"+this.num+"生产完毕,释放机器");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
