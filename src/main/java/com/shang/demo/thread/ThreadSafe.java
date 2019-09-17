package com.shang.demo.thread;

/**
 * <p>
 * 定义了一个退出标志 exit，当 exit 为 true 时，while 循环退出，exit 的默认值为 false.
 * 在定义 exit时，使用了一个 Java 关键字 volatile，这个关键字的目的是使 exit 同步，
 * 也就是说在同一时刻只能由一个线程来修改 exit 的值
 * </p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-09-10 15:56
 */

/*注意: 不要使用 stop方法来终止线程。
stop 方法是很危险的，就象突然关闭计算机电源，而不是按正常程序关机一样，可能会产生不可预料的结果，
不安全主要是:
    thread.stop()调用之后，创建子线程的线程就会抛出 ThreadDeatherror 的错误，并且会释放子
线程所持有的所有锁。一般任何进行加锁的代码块，都是为了保护数据的一致性，如果在调用
    thread.stop()后导致了该线程所持有的所有锁的突然释放(不可控制)，那么被保护数据就有可能呈
现不一致性，其他线程在使用这些被破坏的数据时，有可能导致一些很奇怪的应用程序错误*/

public class ThreadSafe extends Thread {


    public volatile boolean exit = false;

    public void run() {
        while (!exit) {
            //do something
        }
    }

    public void run2() {
        setDaemon(true);//设置线程为“守护线程
        while (!isInterrupted()) {//非阻塞过程中通过判断中断标志来退出
            try {
                Thread.sleep(5*100);//阻塞过程捕获中断异常来退出
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;//捕获到异常之后,执行break 跳出循环
            }
        }
    }


}
