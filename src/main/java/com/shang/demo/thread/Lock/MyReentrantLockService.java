package com.shang.demo.thread.Lock;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试实现ReentrantLock 可重入锁,并发锁
 * ReentrantLock 属于独占锁
 */
@Service
public class MyReentrantLockService {

    private Lock lock =new ReentrantLock();//默认非公平   ,
//    private Lock lock =new ReentrantLock(true); //公平锁
//    private Lock lock =new ReentrantLock(false); //非公平锁

    private Condition condition =lock.newCondition(); //创建condition

    public void reentrantLock(){
        try {
            lock.lock(); //lock 加锁
            //1:wait 方法等待:
            System.out.println("开始wait");
            condition.await(3, TimeUnit.SECONDS);
            //通过创建Condition 对象来使线程wait,必须先执行lock.lock()方法获得锁
            //2:signal 方法唤醒
            condition.signal(); //condition对象的signal()方法可以唤醒wait线程,等效于Object对象的notify()方法
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName="+Thread.currentThread().getName()+(""+(i+1)));

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            /*ReentrantLock 通过方法 lock()与 unlock()来进行加锁与解锁操作，与 synchronized 会
            被 JVM 自动解锁机制不同，ReentrantLock 加锁后需要手动进行解锁。为了避免程序出
            现异常而无法正常解锁的情况，使用 ReentrantLock 必须在 finally 控制块中进行解锁操作*/
            lock.unlock();
            System.out.println("unlock");
        }

    }


}
