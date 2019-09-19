package com.shang.demo.thread.synchronize;

public class DecRunnable implements Runnable {

    private MyData data;

    public DecRunnable(MyData data) {
        this.data=data;

    }

    @Override
    public void run() {
        data.decrease();

    }
}
