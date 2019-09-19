package com.shang.demo.thread.synchronize;

public class AddRunnable implements Runnable {

    private MyData data;

    public AddRunnable(MyData data) {
        this.data=data;

    }

    @Override
    public void run() {
        data.add();

    }
}
