package com.shang.demo.thread.pool;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 自定义线程池--练习Demo文件不能直接使用
 */
public class MyThreadPoolExecutor {

    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
    private TimeUnit unit;
    private BlockingQueue<Runnable> workQueue;  //任务队列


    @Resource
    private  RejectedExecutionHandler handler;  //拒绝策略,当任务太多来不及处理时,如何拒绝任务


    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);

    }
}
