package com.shang.demo.thread;

/**
 * 多线程相关知识点
 */
public class Tips {


    /*线程方法: wait notify notifyAll sleep join yield interrupt
     1 wait() : 线程进入WAITING 状态,会释放对象的锁
     2 sleep() : 不会释放当前对象的锁,进入TIME-WAITING 状态
     3 yield() : 线程让步,当前现场让出CPU的执行时间片,与其他线程一起重新竞争CPU.
     4 interrupt() : 给线程一个信号,会影响这个线程内部的一个中断标识位,而找个线程本身不会立即阻塞或者终止.
     5 join() : 当前线程阻塞,过渡到其他的线程,等待其他线程的终止,然后变为就绪状态
     6 notify() : 唤醒在此对象监视器上等待的随机一个线程
     7 notifyAll(): 唤醒在此对象监视器上等待的所有的线程
     8 enumerate() : 枚举程序中的线程
       isDaemon(): 判断一个线程是否为守护线程
     9 setDaemon() : 设置一个线程为守护线程.(用户线程和守护线程的区别就是,是否依赖与主线程的结束而结束)
     10 setName() : 为线程设置一个名称
     11 activeCount(): 程序中活跃的线程数目
     12 currentThread(): 得到当前线程
     13 setPriority() : 设置一个线程的优先级

     进程: 有时候也叫任务,是指一个程序运行的实例,
     线程: linux中线程就是能并行运行,且与他们的父类进程共享同一地址空间和其他资源的轻量级进程
     上下文: CPU寄存器和程序计数器的内容
     寄存器: 是CPU内部数量较少,但是速度很快的内存,与之对应的是RAM主内存.通常对运算中间值的快速访问,来提高计算机的运行速度.
     程序计数器: 是一个专用的寄存器,存的是正在执行的指令的位置或者下一个将要被执行的指令的位置.
     PCB(进程控制块)--"切换帧": 上下文切换的过程中信息保存在PCB中,

    上下文切换的活动:
     1,挂起一个进程，将这个进程在 CPU 中的状态(上下文)存储于内存中的某处。
     2,在内存中检索下一个进程的上下文并将其在 CPU 的寄存器中恢复。
     3,跳转到程序计数器所指向的位置(即跳转到进程被中断时的代码行)，以恢复该进程在程序中

     同步锁: JAVA中使用synchronized 关键字来取得一个对象的同步锁
     死锁: 多个线程同时被阻塞


     线程复用: 每一个Thread 类都有一个start方法,当调用start 启动线程时,JAVA虚拟机会调用该类的run方法,
     实际上是调用了Runnable 对象的run()方法,
     我们可以在Start() 方法中添加不断循环调用传递来的Runnable对象, 这就是线程池的实现原理.
     线程池的主要工作就是控制运行的线程的数量,处理过程中将任务放入队列.

     线程组成部分:
       1,线程池管理器: 用于创建并管理线程池
       2,工作线程: 线程池中的线程
       3,任务接口: 每个任务必须实现的接口,用于工作线程的调度运行
       4,任务队列: 用于存放待处理的任务,提供一种缓冲机制

     JAVA的线程池是通过Executor 框架实现的

     JAVA的阻塞队列:
        ArrayBlockingQueue :由数组结构组成的有界阻塞队列。
        LinkedBlockingQueue :由链表结构组成的有界阻塞队列。
        PriorityBlockingQueue :支持优先级排序的无界阻塞队列。
        DelayQueue:使用优先级队列实现的无界阻塞队列。
        SynchronousQueue:不存储元素的阻塞队列。
        LinkedTransferQueue:由链表结构组成的无界阻塞队列。
        LinkedBlockingDeque:由链表结构组成的双向阻塞队列

     volatile 关键字:
        volatile 变量是一个中synchronized 关键字更加轻量级的同步机制,
        适用场景: 一个变量被多个线程共享,线程直接给找个变量赋值 ,
        对于volatile的变量,JVM 保证每次读取变量都是从内存中读取,跳过了CPU的Cache这一步
     注意:
        volatile 变量不能保证i++ 这种操作的原子性
        1,对变量的写操作,不依赖与当前值,或者说是单纯的变量的赋值
        2,该变量没有包含在具有其他变量的的不变式中,也就是说volatile变量之间不能够互相依赖,只有状态真正独立于程序内的其他内容时候才能使用volatile

     ThreadLocal(线程本地变量):
        也叫线程本地存储,作用是提供线程内的局部变量,只在线程的生命周期内起作用, 方便同一个线程内部多个函数或组件之间公共变量的传递

     ThreadLocalMap(线程的一个属性)
        每个线程都有一个自己的ThreadLocalMap类对象,
        将一个共用的 ThreadLocal 静态实例作为 key，将不同对象的引用保存到不同线程的
        ThreadLocalMap 中，然后在线程执行的各处通过这个静态 ThreadLocal 实例的 get()方法取
        得自己线程保存的那个对象，避免了将这个对象作为参数传递的麻烦
        ThreadLocalMap<ThreadLocal, T> threadLocals
        set(v){
           Thread.currentThread().threadLocalMap().put(threadLocal, v);
        }
        get(){
           Thread.currentThread().threadLocalMap().get(threadLocal);
        }
        remove(){
           Thread.currentThread().threadLocalMap().remove(threadLocal);
        }


     ConcurrentHashMap 分段锁
        concurrentHashMap 内部细分了若干个小的hashMap,称之为段(Segment),
        默认情况下一个ConcurrentHashMap 分为16个Segment ,即为锁的并发度.

     原子包: java.util.concurrent.atomic(锁自旋)
         这个包中一些原子类,当某个线程进入原子类的某个方法,执行其中的指令时,不会被其他线程打断,
         而别的线程就像自旋锁一样,一直等到该方法执行完成,


     同步器的实现是 ABS 核心(state 资源状态计数)
        同步器的实现是 ABS 核心，以 ReentrantLock 为例，state 初始化为 0，表示未锁定状态。A 线程
        lock()时，会调用 tryAcquire()独占该锁并将 state+1。此后，其他线程再 tryAcquire()时就会失
        败，直到 A 线程 unlock()到 state=0(即释放锁)为止，其它线程才有机会获取该锁。当然，释放
        锁之前，A 线程自己是可以重复获取此锁的(state 会累加)，这就是可重入的概念。但要注意，
        获取多少次就要释放多么次，这样才能保证 state 是能回到零态的。
        以 CountDownLatch 以例，任务分为 N 个子线程去执行，state 也初始化为 N(注意 N 要与
        线程个数一致)。这 N 个子线程是并行执行的，每个子线程执行完后 countDown()一次，state
        会 CAS 减 1。等到所有子线程都执行完后(即 state=0)，会 unpark()主调用线程，然后主调用线程
        就会从 await()函数返回，继续后余动作。
        ReentrantReadWriteLock 实现独占和共享两种方式
        一般来说，自定义同步器要么是独占方法，要么是共享方式，他们也只需实现 tryAcquire-
        tryRelease、tryAcquireShared-tryReleaseShared 中的一种即可。但 AQS 也支持自定义同步器
        同时实现独占和共享两种方式，如 ReentrantReadWriteLock


     */



}
