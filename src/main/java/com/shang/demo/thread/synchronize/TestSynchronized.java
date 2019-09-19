package com.shang.demo.thread.synchronize;

/**
 * 测试类
 */
public class TestSynchronized {

//    public static void main(String[] args) {
//        MyData myData = new MyData();
//        AddRunnable addRunnable = new AddRunnable(myData);
//        DecRunnable decRunnable = new DecRunnable(myData);
//
//        for (int i = 0; i < 2; i++) {
//            new Thread(addRunnable).start();
//            new Thread(decRunnable).start();
//        }
//    }

    public static void main(String[] args) {
        final MyData data =new MyData();

        for (int i = 0; i < 2; i++) {
            //普通写法
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data.add();
                }
            }).start();

            //lambda 表达式写法
//            new Thread(() -> data.decrease()).start();
            new Thread(data::decrease).start();
        }

    }
}
