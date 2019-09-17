package com.shang.demo.thread.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 读写锁 ,属于共享锁
 * 如果你的代码只读数据，可以很多人同时读，但不能同时写，那就上读锁
 * 如果你的代码修改数据,只能有一个人在写,且不能同时读取,那就上写锁,
 * 总之:读的时候上读锁,写的时候上写锁
 */
public class MyReadWriteLock {

    public static void main(String[] args) {


        Lock lock = new ReadWriteLock() {
            @Override
            public Lock readLock() {
                return null;
            }

            @Override
            public Lock writeLock() {
                return null;
            }
        }.writeLock();

    }


}
